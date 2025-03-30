package com.example.airhop.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * App container for dependency injection.
 */
interface AppContainer {
    val airportsRepository: AirportsRepository
    val favoritesRepository: FavoritesRepository
    val preferenceRepository: PreferenceRepository
}

/**
 * [AppContainer] implementation that provide instances of [OfflineAirportsRepository]
 * and [OfflineFavoritesRepository].
 */
class DefaultContainer(
    private val context: Context,
    private val dataStore: DataStore<Preferences>
) : AppContainer {

    override val airportsRepository: AirportsRepository by lazy {
        OfflineAirportsRepository(AirHopDatabase.getDatabase(context).airportDao())
    }

    override val favoritesRepository: FavoritesRepository by lazy {
        OfflineFavoritesRepository(AirHopDatabase.getDatabase(context).favoriteDao())
    }
    override val preferenceRepository: PreferenceRepository by lazy {
        UserPreferencesRepository(dataStore)
    }
}

