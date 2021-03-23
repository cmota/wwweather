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
package com.example.androiddevchallenge.presentation

import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.domain.GetWeatherData
import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.domain.model.WeatherData
import com.example.androiddevchallenge.presentation.cb.WeatherDataEvent
import com.example.androiddevchallenge.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(), WeatherDataEvent {

    private val _weather = SingleLiveEvent<WeatherData>()
    val weather: SingleLiveEvent<WeatherData> = _weather

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getWeather(city: City, units: UNITS) {
        val event = this
        scope.launch {
            GetWeatherData().invoke(city, units, event)
        }
    }

    //region WeatherDataEvent

    override fun onWeatherDataFetched(data: WeatherData) {
        weather.postValue(data)
    }

    override fun onWeatherDataFailed() {
        weather.postValue(null)
    }

    //endregion
}
