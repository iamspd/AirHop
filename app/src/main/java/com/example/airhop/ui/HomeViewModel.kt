package com.example.airhop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airhop.data.Airport
import com.example.airhop.data.AirportsRepository
import com.example.airhop.data.Favorite
import com.example.airhop.data.FavoritesRepository
import com.example.airhop.data.Flight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val airportsRepository: AirportsRepository
) : ViewModel() {
    companion object {
        private const val SUBSCRIBER_TIMEOUT_MILLIS = 5_000L
        private const val DEBOUNCE_TIMEOUT_MILLIS = 300L
    }

    /**
     * Emit favorite flights from the repository.
     */
    val favoriteUiState: StateFlow<FavoriteUiState> =
        favoritesRepository.getFavoriteFlights().map { FavoriteUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(SUBSCRIBER_TIMEOUT_MILLIS),
                initialValue = FavoriteUiState()
            )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateQueryString(query: String) {
        _searchQuery.value = query
    }

    /**
     * Create airport list based on search query in the Search Bar composable.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val airportList: StateFlow<List<Airport>> =
        _searchQuery
            .debounce(DEBOUNCE_TIMEOUT_MILLIS)
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(emptyList())
                } else {
                    airportsRepository.getAirportsStream(query)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(SUBSCRIBER_TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

    /**
     * Get and update current Search Bar state (i.e. expanded or not)
     * based on user interaction.
     */
    private val _searchBarState = MutableStateFlow(value = false)
    val searchBarState: StateFlow<Boolean> = _searchBarState.asStateFlow()

    fun updateExpandedState(isExpanded: Boolean) {
        _searchBarState.value = isExpanded
    }

    /**
     * Current selected airport from the SearchBar content composable.
     */
    private val _selectedAirport = MutableStateFlow(value = Airport())
    val selectedAirport: StateFlow<Airport> = _selectedAirport.asStateFlow()

    fun getSelectedAirport(airport: Airport) {
        _selectedAirport.value = airport
    }

    /**
     * Create flight list by pairing [_selectedAirport]
     * with rest of the airports in the list.
     */
    fun searchFlights(): Flow<List<Flight>> {
        return airportsRepository.getAllAirportsStream().map { airports ->
            val flights = mutableListOf<Flight>()
            airports.filter { departure -> departure.code != _selectedAirport.value.code }
                .forEach { destination ->
                    flights.add(
                        Flight(
                            departureAirport = _selectedAirport.value,
                            destinationAirport = destination
                        )
                    )
                }
            flights
        }
    }

    /**
     * Insert into [Favorite] entity.
     */
    fun addToFavorite(flight: Flight) {
        viewModelScope.launch {
            favoritesRepository.insertFlight(
                Favorite(
                    departureCode = flight.departureAirport.code,
                    destinationCode = flight.destinationAirport.code
                )
            )
        }
    }

    /**
     * Delete from [Favorite] entity.
     */
    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoritesRepository.deleteFlight(favorite)
        }
    }
}

/**
 * UI state of Favorite List.
 */
data class FavoriteUiState(val favorites: List<Favorite> = listOf())