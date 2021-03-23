package com.example.androiddevchallenge.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.domain.model.Daily
import com.example.androiddevchallenge.domain.model.mapper
import com.example.androiddevchallenge.presentation.WeatherViewModel
import com.example.androiddevchallenge.ui.theme.typography
import com.example.androiddevchallenge.utils.formatUnit
import com.example.androiddevchallenge.utils.getDayOfWeek
import com.example.androiddevchallenge.utils.getWeatherIcon
import com.example.androiddevchallenge.utils.roundUp
import java.util.*

private const val TAG = "MainScreen"

@Composable
fun MainScreen(viewModel: WeatherViewModel,
               darkTheme: Boolean, units: UNITS,
) {

    val weatherData by viewModel.weather.observeAsState()

    val selection = remember { mutableStateOf(0) }

    val weather = mapper(weatherData, selection.value)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(60.dp))

        Log.d(TAG, "Icon=${weather.icon} | now=${weather.now} | selected=${selection.value}")

        val icon = painterResource(getWeatherIcon(weather.icon, darkTheme))
        val description = stringResource(id = R.string.description_weather)

        Image(
            painter = icon,
            contentDescription = description,
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(id = R.string.feels_like),
            style = typography.h1
        )

        val context = LocalContext.current

        Text(
            text = formatUnit(context, roundUp(weather.feelsLike), units, true),
            style = typography.h2
        )

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {

            AddMoreInformation(R.string.temperature_now, formatUnit(context, roundUp(weather.now), units, true))

            val min = formatUnit(context, roundUp(weather.min), units, true)
            val max = formatUnit(context, roundUp(weather.max), units, true)
            AddMoreInformation(R.string.temperature_min_max, "$min / $max")

            AddMoreInformation(R.string.temperature_wind, formatUnit(context, roundUp(weather.wind), units, false))

            val value = roundUp(weather.humidity)
            val humidity = if(value != null) {
                value + stringResource(id = R.string.unit_percentage)
            } else {
                "-"
            }
            AddMoreInformation(
                R.string.temperature_humidity,
                humidity
            )

            Spacer(modifier = Modifier.height(40.dp))

            val forecast = if (weather.daily.isEmpty()) {
                val daily = Daily()
                listOf(daily, daily, daily, daily, daily)
            } else {
                weather.daily.subList(0, 5)
            }

            LazyRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {

                itemsIndexed(forecast) { index, item ->
                    AddForecast(item, index, selection, darkTheme, units)
                }
            }
        }
    }
}

@Composable
fun AddMoreInformation(@StringRes resId: Int, value: String?) {
    Row {

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f)
            .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End) {
            Text(
                text = stringResource(id = resId),
                style = typography.h3
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f)
            .padding(start = 10.dp),
            horizontalArrangement = Arrangement.Start) {
            Text(
                text = value?:"-",
                style = typography.h4
            )
        }
    }
}

@Composable
fun AddForecast(temp: Daily, index: Int, selection: MutableState<Int>, darkTheme: Boolean, units: UNITS) {
    val selected = index == selection.value
    
    val background = if (selected) {
        MaterialTheme.colors.primaryVariant
    } else {
        MaterialTheme.colors.secondaryVariant
    }

    val textColor = if (selected) {
        MaterialTheme.colors.secondaryVariant
    } else {
        MaterialTheme.colors.primaryVariant
    }

    Card(
        modifier = Modifier
            .width(90.dp)
            .height(145.dp)
            .padding(start = 8.dp, bottom = 16.dp, end = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                selection.value = index    
            },
        backgroundColor = background,
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, index)

            Text(
                text = getDayOfWeek(LocalContext.current, index),
                style = typography.h5,
                color = textColor
            )

            val dailyIcon = painterResource(getWeatherIcon(temp.weather[0].icon, if(selected) !darkTheme else darkTheme))
            val description = stringResource(id = R.string.description_weather)

            Image(
                painter = dailyIcon,
                contentDescription = description,
                modifier = Modifier.width(45.dp)
            )

            val context = LocalContext.current
            Text(
                text = formatUnit(context, roundUp(temp.temp.day), units, true),
                style = typography.h5,
                color = textColor
            )
        }
    }
}