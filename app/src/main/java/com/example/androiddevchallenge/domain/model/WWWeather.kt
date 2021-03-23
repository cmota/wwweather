package com.example.androiddevchallenge.domain.model

data class WWWeather(val icon: String = "",
                     val feelsLike: Double = 0.0,
                     val now: Double = 0.0,
                     val min: Double = 0.0,
                     val max: Double = 0.0,
                     val wind: Double = 0.0,
                     val humidity: Int = 0,
                     val daily: List<Daily> = emptyList())

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