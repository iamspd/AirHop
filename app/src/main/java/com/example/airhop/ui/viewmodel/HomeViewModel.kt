package com.example.airhop.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airhop.data.model.Airport
import com.example.airhop.data.repository.AirportsRepository
import com.example.airhop.data.model.Flight
import com.example.airhop.data.repository.PreferenceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
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
    private val airportsRepository: AirportsRepository,
    private val userPreferencesRepository: PreferenceRepository
) : ViewModel() {
    companion object {
        const val SUBSCRIBER_TIMEOUT_MILLIS = 5_000L
        private const val DEBOUNCE_TIMEOUT_MILLIS = 500L
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateQueryString(query: String) {
        _searchQuery.value = query

        /** Adding a delay before updating the [_searchQuery] so that DataStore
         * does not keep writing at every key-stroke.
         **/
        viewModelScope.launch {
            delay(SUBSCRIBER_TIMEOUT_MILLIS)
            userPreferencesRepository.saveSearchQuery(query)
        }
    }

    /**
     * Check if the DataStore Preferences has the savedQuery on the start.
     */
    init {
        viewModelScope.launch {
            userPreferencesRepository.getSearchQueryPref().collect { savedQuery ->
                if (_searchQuery.value.isBlank()) {
                    _searchQuery.value = savedQuery
                }
            }

        }
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

    fun updateSearchBarState(isExpanded: Boolean) {
        _searchBarState.value = isExpanded
    }

    /**
     * Current selected airport from the SearchBar content composable.
     */
    private val _departureAirport = MutableStateFlow(value = Airport())
    fun getDepartureAirport(airport: Airport) {
        _departureAirport.value = airport
    }

    /**
     * A flight list by pairing [_departureAirport]
     * with rest of the airports in the list.
     */
    fun searchFlights(): Flow<List<Flight>> {
        return airportsRepository.getAllAirportsStream().map { airports ->
            val flights = mutableListOf<Flight>()
            airports.filter { airport -> airport.code != _departureAirport.value.code }
                .forEach { destinationAirport ->
                    flights.add(
                        Flight(
                            departureAirport = _departureAirport.value,
                            destinationAirport = destinationAirport
                        )
                    )
                }
            flights
        }
    }

    private val _homeScreenUiState = MutableStateFlow(value = true)
    val homeScreenUiState: StateFlow<Boolean> = _homeScreenUiState.asStateFlow()

    fun updateHomeScreenUiState(state: Boolean) {
        _homeScreenUiState.value = state
    }
}