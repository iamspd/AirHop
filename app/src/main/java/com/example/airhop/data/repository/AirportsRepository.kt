package com.example.airhop.data.repository

import com.example.airhop.data.model.Airport
import kotlinx.coroutines.flow.Flow

/**
 * Repository that gets the airport information from given string of
 * IATA code or airport name.
 */
interface AirportsRepository {
    fun getAirportsStream(query: String): Flow<List<Airport>>

    fun getAllAirportsStream(): Flow<List<Airport>>
}