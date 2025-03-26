package com.example.airhop.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object class for [Favorite] table that contains all queries
 * to operate on the data.
 */
@Dao
interface FavoriteDao {
    /**
     * The table is initially empty. Hence, we'll insert the data first.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    /**
     * User may decide to remove/unselect the favorite airport.
     */
    @Delete
    suspend fun delete(favorite: Favorite)

    /**
     * Get all [Favorite] airports.
     */
    @Query("SELECT * FROM favorite ORDER BY departure_code ASC")
    fun getFavorites(): Flow<List<Favorite>>
}