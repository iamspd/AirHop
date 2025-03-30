package com.example.airhop

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.airhop.data.AppContainer
import com.example.airhop.data.DefaultContainer

/**
 * [AppContainer] instance used by the rest of the classes to obtain dependencies.
 */

const val LAYOUT_PREFERENCE_NAME = "layout_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

class AirHopApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultContainer(
            context = this,
            dataStore = this.dataStore
        )
    }
}