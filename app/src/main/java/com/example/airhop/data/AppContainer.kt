package com.example.airhop.data

import android.content.Context

/**
 * App container for dependency injection.
 */
interface AppContainer {
    val airportsRepository: AirportsRepository
    val favoritesRepository: FavoritesRepository
}

/**
 * [AppContainer] implementation that provide instances of [OfflineAirportsRepository]
 * and [OfflineFavoritesRepository].
 */
class DefaultContainer(private val context: Context) : AppContainer {

    override val airportsRepository: AirportsRepository by lazy {
        OfflineAirportsRepository(AirHopDatabase.getDatabase(context).airportDao())
    }

    override val favoritesRepository: FavoritesRepository by lazy {
        OfflineFavoritesRepository(AirHopDatabase.getDatabase(context).favoriteDao())
    }
}

