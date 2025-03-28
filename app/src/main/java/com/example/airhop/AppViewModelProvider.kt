package com.example.airhop

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.airhop.ui.HomeViewModel
import android.app.Application

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                airHopApplication().appContainer.favoritesRepository,
                airHopApplication().appContainer.airportsRepository
            )
        }
    }
}

/**
 * Extension function that queries for [Application] object and returns an instance of
 * [AirHopApplication]
 */
fun CreationExtras.airHopApplication(): AirHopApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AirHopApplication