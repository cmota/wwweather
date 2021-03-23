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
package com.example.androiddevchallenge.domain.model

data class WWWeather(
    val icon: String = "",
    val feelsLike: Double = 0.0,
    val now: Double = 0.0,
    val min: Double = 0.0,
    val max: Double = 0.0,
    val wind: Double = 0.0,
    val humidity: Int = 0,
    val daily: List<Daily> = emptyList()
)

fun mapper(weatherData: WeatherData?, index: Int): WWWeather {
    if (weatherData == null) {
        return WWWeather()
    }

    val daily = if (weatherData.daily.size > index) {
        weatherData.daily[index]
    } else {
        Daily()
    }

    return WWWeather(
        icon = daily.weather[0].icon,
        feelsLike = daily.feels_like.day,
        now = daily.temp.day,
        min = daily.temp.min,
        max = daily.temp.max,
        wind = daily.wind_speed,
        humidity = daily.humidity,
        daily = weatherData.daily
    )
}
