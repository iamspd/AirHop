package com.example.airhop.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Favorite] entity data class that represents the favorite table
 * from the air_hop db.
 */
@Entity(tableName = "favorite")
data class Favorite(
    /** Uniquely identifies the favorite airport in the table. **/
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /** Internation air transport association (IATA) code of departing airport. **/
    @ColumnInfo(name = "departure_code")
    val departureCode: String = "YYZ",
    /** Name of departure airport. **/
    @ColumnInfo(name = "departure_name")
    val departureName: String = "Toronto Pearson",
    /** IATA code of destination airport. **/
    @ColumnInfo(name = "destination_code")
    val destinationCode: String = "YYZ",
    /** Name of destination airport. **/
    @ColumnInfo(name = "destination_name")
    val destinationName: String = "Toronto Pearson"
)
