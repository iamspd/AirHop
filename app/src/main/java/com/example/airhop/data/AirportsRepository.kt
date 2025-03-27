package com.example.airhop.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that gets the airport information from given string of
 * IATA code or airport name.
 */
interface AirportsRepository {
    fun getFlightsStream(query: String): Flow<List<Airport>>
}