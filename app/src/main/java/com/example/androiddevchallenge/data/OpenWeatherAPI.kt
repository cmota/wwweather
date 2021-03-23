/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.domain.model.WeatherData
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
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
