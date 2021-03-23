package com.example.androiddevchallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val alerts: List<Alert> = emptyList(),
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)