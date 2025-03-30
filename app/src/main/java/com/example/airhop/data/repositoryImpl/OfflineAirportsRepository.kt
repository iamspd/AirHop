package com.example.airhop.data.repositoryImpl

import com.example.airhop.data.AirportDao
import com.example.airhop.data.model.Airport
import com.example.airhop.data.repository.AirportsRepository
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