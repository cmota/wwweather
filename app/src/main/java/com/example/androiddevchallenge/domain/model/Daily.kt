package com.example.androiddevchallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    val clouds: Int = 0,
    val dew_point: Double = 0.0,
    val dt: Int = 0,
    val feels_like: FeelsLike = FeelsLike(),
    val humidity: Int = 0,
    val pop: Double = 0.0,
    val pressure: Int = 0,
    val rain: Double? = 0.0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val temp: Temp = Temp(),
    val uvi: Double = 0.0,
    val weather: List<Weather> = listOf(Weather()),
    val wind_deg: Int = 0,
    val wind_speed: Double = 0.0
)