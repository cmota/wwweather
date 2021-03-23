package com.example.androiddevchallenge.utils

import android.content.Context
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.ui.theme.DarkImages
import com.example.androiddevchallenge.ui.theme.LightImages
import java.util.*

fun roundUp(value: Double?): String? {
    if (value == null) {
        return null
    }

    return "${Math.round(value)}"
}

fun roundUp(value: Int?): String? {
    return roundUp(value?.toDouble())
}

fun formatUnit(context: Context, value: String?, unit: UNITS, temp: Boolean): String {
    if (value == null || value.isEmpty()) {
        return "-"
    }

    return when(unit) {
        UNITS.METRIC -> {
            if (temp) {
                context.getString(R.string.unit_celsius, value)
            } else {
                context.getString(R.string.unit_kmh, value)
            }
        }
        UNITS.IMPERIAL -> {
            if (temp) {
                context.getString(R.string.unit_imperial, value)
            } else {
                context.getString(R.string.unit_mph, value)
            }
        }
    }
}

fun getDayOfWeek(context: Context, index: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, index)

    return if (index == 0) {
        context.getString(R.string.today)
    } else {
        calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())!!
    }
}

fun getWeatherIcon(icon: String, darkTheme: Boolean): Int {
    if (icon.isNotEmpty()) {
        when(icon) {
            "01d", "01n" -> { return if(darkTheme) DarkImages[0] else LightImages[0]}
            "02d", "02n" -> { return if(darkTheme) DarkImages[1] else LightImages[1]}
            "03d", "03n" -> { return if(darkTheme) DarkImages[2] else LightImages[2]}
            "04d", "04n" -> { return if(darkTheme) DarkImages[3] else LightImages[3]}
            "09d", "09n" -> { return if(darkTheme) DarkImages[4] else LightImages[4]}
            "10d", "10n" -> { return if(darkTheme) DarkImages[5] else LightImages[5]}
            "11d", "11n" -> { return if(darkTheme) DarkImages[6] else LightImages[6]}
            "13d", "13n" -> { return if(darkTheme) DarkImages[7] else LightImages[7]}
            "50d", "50n" -> { return if(darkTheme) DarkImages[8] else LightImages[8]}
        }
    }

    return if(darkTheme) DarkImages[1] else LightImages[1]
}