package com.example.airhop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airhop.data.Favorite
import com.example.airhop.data.FavoritesRepository
import com.example.airhop.data.Flight
import com.example.airhop.ui.HomeViewModel.Companion.SUBSCRIBER_TIMEOUT_MILLIS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

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

    /**
     * Insert data into [Favorite] entity.
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