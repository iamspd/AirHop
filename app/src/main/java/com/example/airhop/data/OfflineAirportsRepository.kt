package com.example.airhop.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportsRepository(
    private val airportDao: AirportDao
) : AirportsRepository {
    override fun getAirportsStream(query: String): Flow<List<Airport>> {
        return airportDao.getAirports(query)
    }

    override fun getAllAirportsStream(): Flow<List<Airport>> {
        return airportDao.getAllAirports()
    }
}