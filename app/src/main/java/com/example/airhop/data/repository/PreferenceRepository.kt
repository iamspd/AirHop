package com.example.airhop.data.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun saveSearchQuery(searchQuery: String)
    fun getSearchQueryPref(): Flow<String>
}