package com.example.airhop.data

/**
 * Data class that represents flight information based on
 * [departureAirport] and [destinationAirport].
 */
data class Flight(
    val departureAirport: Airport,
    val destinationAirport: Airport
)
