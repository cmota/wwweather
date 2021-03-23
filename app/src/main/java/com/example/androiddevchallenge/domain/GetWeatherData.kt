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