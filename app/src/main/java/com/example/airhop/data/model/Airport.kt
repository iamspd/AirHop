package com.example.airhop.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Airport] Entity data class that represents airport table
 * from air_hop db.
 */
@Entity(tableName = "airport")
data class Airport(
    /** Uniquely identifies airport entries in the table - primary key.  **/
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /**
     * Internation airport transport association code - will be used to search airports by users
     * from the search box.
     **/
    @ColumnInfo(name = "iata_code")
    val code: String = "YYZ",
    /** Airport name that a user can search for in the search box. **/
    @ColumnInfo(name = "name")
    val name: String = "Toronto Pearson Airport",
    /** Passengers visiting annually to the given airport. **/
    @ColumnInfo(name = "passengers")
    val annualPassengerVisits: Int = 1_000_000
)
