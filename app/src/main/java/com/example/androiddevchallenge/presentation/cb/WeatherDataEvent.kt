package com.example.androiddevchallenge.presentation.cb

import com.example.androiddevchallenge.domain.model.WeatherData

interface WeatherDataEvent {

    fun onWeatherDataFetched(data: WeatherData)

    fun onWeatherDataFailed()
}