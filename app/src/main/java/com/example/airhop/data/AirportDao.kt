package com.example.airhop.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Object Access class for [Airport] table that contains all queries
 * to operate on the data.
 */
@Dao
interface AirportDao {

    /**
     * The [Airport] details will be displayed based on given IATA code
     * OR the airport name.
     */
    @Query(
        """
       SELECT * FROM airport WHERE name LIKE '%' || :query || '%' 
OR iata_code LIKE '%' || :query || '%'
    """
    )
    fun getFlights(query: String): Flow<List<Airport>>
}