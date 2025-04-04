package com.example.airhop

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.airhop.data.AirHopDatabase
import com.example.airhop.data.AirportDao
import com.example.airhop.data.FavoriteDao
import com.example.airhop.data.model.Airport
import com.example.airhop.data.model.Favorite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class AppDaoTest {
    private lateinit var airportDao: AirportDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var airHopDatabase: AirHopDatabase

    private val airport1 = Airport(
        id = 28,
        code = "MUC",
        name = "Munich International Airport",
        annualPassengerVisits = 47959885
    )
    private val favoriteAirport1 = Favorite(
        id = 1,
        departureCode = "YYZ",
        destinationCode = "MUC"
    )
    private val favoriteAirport2 = Favorite(
        id = 2,
        departureCode = "MUC",
        destinationCode = "YYZ"
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        /**
         * Creating the database on disk because we are using pre-populated database
         * from the asset file.
         */
        airHopDatabase =
            Room.databaseBuilder(context, AirHopDatabase::class.java, name = "test_airhop.db")
                .createFromAsset(databaseFilePath = "database/air_hop.db")
                .allowMainThreadQueries() // Allowing main thread queries for testing
                .build()
        airportDao = airHopDatabase.airportDao()
        favoriteDao = airHopDatabase.favoriteDao()
    }

    @Test
    @Throws(Exception::class)
    fun airportDao_getAllAirports_verifyAirportData() = runBlocking {

        val airports = airportDao.getAirports("MUC").first()
        assertEquals(airports[0], airport1)
    }

    private suspend fun addOneItemToFavoriteTable() {
        favoriteDao.insert(favoriteAirport1)
    }

    private suspend fun addTwoItemsToFavoriteTable() {
        favoriteDao.insert(favoriteAirport1)
        favoriteDao.insert((favoriteAirport2))
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_insertOneItem_verifyInsertedItem() = runBlocking {
        addOneItemToFavoriteTable()

        val allFavorites = favoriteDao.getFavorites().first()
        assertEquals(allFavorites[0], favoriteAirport1)
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_getAllItems_verifyTheOrder() = runBlocking {
        addTwoItemsToFavoriteTable()

        val allFavorites = favoriteDao.getFavorites().first()
        assertEquals(allFavorites[0], favoriteAirport2)
        assertEquals(allFavorites[1], favoriteAirport1)
    }

    @Test
    @Throws(Exception::class)
    fun favoriteDao_deleteAllItems_verifyTableIsEmpty() = runBlocking {
        addOneItemToFavoriteTable()
        favoriteDao.delete(favoriteAirport1)

        val allFavorites = favoriteDao.getFavorites().first()
        assertTrue(allFavorites.isEmpty())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        airHopDatabase.close()
    }
}