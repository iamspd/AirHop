package com.example.airhop.data

import kotlinx.coroutines.flow.Flow

class OfflineAirportsRepository(
    private val airportDao: AirportDao
) : AirportsRepository {
    override fun getFlightsStream(searchString: String): Flow<List<Airport>> {
        return airportDao.getFlights(searchString)
    }
}