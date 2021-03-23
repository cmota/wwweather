package com.example.androiddevchallenge.presentation

import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.domain.GetWeatherData
import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.utils.SingleLiveEvent
import com.example.androiddevchallenge.domain.model.WeatherData
import com.example.androiddevchallenge.presentation.cb.WeatherDataEvent
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