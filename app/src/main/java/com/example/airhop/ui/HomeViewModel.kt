package com.example.airhop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airhop.data.Airport
import com.example.airhop.data.AirportsRepository
import com.example.airhop.data.Favorite
import com.example.airhop.data.FavoritesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    favoritesRepository: FavoritesRepository,
    airportsRepository: AirportsRepository
) : ViewModel() {
    companion object {
        private const val SUBSCRIBER_TIMEOUT_MILLIS = 5_000L
        private const val DEBOUNCE_TIMEOUT_MILLIS = 300L
    }

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
}

/**
 * UI state of Favorite List.
 */
data class FavoriteUiState(val favorites: List<Favorite> = listOf())