package com.example.airhop.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that insert, delete and fetches the favorite airport data
 * from [Favorite] entity.
 */
interface FavoritesRepository {

    /** Insert the flight info to the entity.  **/
    suspend fun insertFlight(favorite: Favorite)

    /** Delete the flight data from the entity. **/
    suspend fun deleteFlight(favorite: Favorite)

    /** Retrieve all favorite flights. **/
    fun getFavoriteFlights(): Flow<List<Favorite>>
}