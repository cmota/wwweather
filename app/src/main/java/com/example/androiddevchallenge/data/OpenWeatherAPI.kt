package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.domain.model.WeatherData
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json


private const val API_KEY = "3404b86afb31bf57ef44a72bc8f12405"

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/onecall"
private const val PARAM_LAT = "?lat="
private const val PARAM_LON = "&lon="
private const val PARAM_UNITS = "&units="
private const val PARAM_EXCLUDE = "&exclude=minutely,hourly"
private const val PARAM_KEY = "&appid=$API_KEY"

private const val METRIC = "metric"
private const val IMPERIAL = "imperial"

enum class UNITS { METRIC, IMPERIAL }

object OpenWeatherAPI {

    private val nonStrictJson = Json { isLenient = true; ignoreUnknownKeys = true }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(nonStrictJson)
        }
    }

    suspend fun getWeather(lat: String, lon: String, units: UNITS): WeatherData = client.get(
        BASE_URL +
                "$PARAM_LAT$lat" +
                "$PARAM_LON$lon" +
                "$PARAM_UNITS$units" +
                PARAM_EXCLUDE +
                PARAM_KEY
    )
}