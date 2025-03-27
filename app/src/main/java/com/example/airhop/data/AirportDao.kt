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
       SELECT * FROM airport WHERE name LIKE '%' || :searchString || '%' 
OR iata_code LIKE '%' || :searchString || '%'
    """
    )
    fun getFlights(searchString: String): Flow<List<Airport>>
}