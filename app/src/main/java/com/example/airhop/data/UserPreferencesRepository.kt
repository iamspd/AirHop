package com.example.airhop.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Preference Repository class that stores search query in the search bar
 * composable.
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : PreferenceRepository {
    private companion object {
        val SEARCH_QUERY = stringPreferencesKey("searchQuery")
        const val TAG = "UserPreferenceRepo"
    }

    override suspend fun saveSearchQuery(searchQuery: String) {
        dataStore.edit { preference ->
            preference[SEARCH_QUERY] = searchQuery
        }
    }

    override fun getSearchQueryPref(): Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            preference[SEARCH_QUERY] ?: ""
        }
}