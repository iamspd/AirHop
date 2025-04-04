package com.example.airhop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.airhop.data.model.Airport
import com.example.airhop.data.model.Favorite

/**
 * Database instance class to instantiate the air_hop db.
 */
@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class AirHopDatabase : RoomDatabase() {

    // Create DAO instances
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: AirHopDatabase? = null

        fun getDatabase(context: Context): AirHopDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = AirHopDatabase::class.java,
                    name = "air_hop_database"
                )
                    .createFromAsset(databaseFilePath = "database/air_hop.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}