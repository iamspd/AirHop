package com.example.airhop

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.airhop.ui.viewmodel.HomeViewModel
import android.app.Application
import com.example.airhop.ui.viewmodel.FavoriteViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                AirHopApplication().appContainer.airportsRepository,
                AirHopApplication().appContainer.preferenceRepository
            )
        }
        initializer {
            FavoriteViewModel(
                AirHopApplication().appContainer.favoritesRepository
            )
        }
    }
}

/**
 * Extension function that queries for [Application] object and returns an instance of
 * [AirHopApplication]
 */
fun CreationExtras.AirHopApplication(): AirHopApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AirHopApplication