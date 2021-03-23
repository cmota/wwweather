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
package com.example.androiddevchallenge.domain

import android.util.Log
import com.example.androiddevchallenge.data.OpenWeatherAPI
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.presentation.cb.WeatherDataEvent
import kotlinx.coroutines.coroutineScope

private const val TAG = "GetWeatherData"

class GetWeatherData {

    suspend fun invoke(city: City, units: UNITS, event: WeatherDataEvent) {
        try {

            val result = OpenWeatherAPI.getWeather(city.lat, city.lon, units)

            Log.d(TAG, "Result=$result")

            coroutineScope {
                event.onWeatherDataFetched(result)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Unable to get data for city=$city. Reason=$e")

            coroutineScope {
                event.onWeatherDataFailed()
            }
        }
    }
}
