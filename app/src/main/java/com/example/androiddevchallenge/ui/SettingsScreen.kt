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
package com.example.androiddevchallenge.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.presentation.WeatherViewModel
import com.example.androiddevchallenge.ui.theme.typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class SELECTION { OPTION_1, OPTION_2 }
enum class OPTIONS { SETTINGS, SEARCH }

@Composable
fun SettingsScreen(units: MutableState<UNITS>, darkTheme: MutableState<Boolean>) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.settings),
            style = typography.h4
        )

        Spacer(modifier = Modifier.height(40.dp))

        val metricSelected = remember { mutableStateOf(SELECTION.OPTION_1) }
        units.value = if (metricSelected.value == SELECTION.OPTION_1) {
            UNITS.METRIC
        } else {
            UNITS.IMPERIAL
        }

        SelectOption(
            R.string.units,
            R.string.units_metric,
            R.string.units_imperial,
            metricSelected
        ) {}

        Spacer(modifier = Modifier.height(40.dp))

        val option = if (darkTheme.value) {
            SELECTION.OPTION_2
        } else {
            SELECTION.OPTION_1
        }

        val themeSelected = remember { mutableStateOf(option) }

        SelectOption(
            R.string.theme,
            R.string.theme_light,
            R.string.theme_dark,
            themeSelected
        ) {
            darkTheme.value = themeSelected.value != SELECTION.OPTION_1
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun SelectOption(
    @StringRes label: Int,
    @StringRes option1: Int,
    @StringRes option2: Int,
    selected: MutableState<SELECTION>,
    action: () -> Unit
) {

    Row {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f),
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                text = stringResource(id = label),
                style = typography.h4
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.0f)
                .padding(start = 30.dp),
            horizontalArrangement = Arrangement.Start
        ) {

            Column {

                Row {

                    RadioButton(
                        selected = selected.value == SELECTION.OPTION_1,
                        onClick = {
                            selected.value = SELECTION.OPTION_1
                            action()
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.onSecondary,
                            unselectedColor = MaterialTheme.colors.onSecondary
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(id = option1),
                        style = typography.h3
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    RadioButton(
                        selected = selected.value == SELECTION.OPTION_2,
                        onClick = {
                            selected.value = SELECTION.OPTION_2
                            action()
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.onSecondary,
                            unselectedColor = MaterialTheme.colors.onSecondary
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(id = option2),
                        style = typography.h3
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchScreen(viewModel: WeatherViewModel, units: UNITS, coroutineScope: CoroutineScope, bottomSheetScaffoldState: BottomSheetScaffoldState) {

    Column(
        modifier = Modifier.padding(30.dp)
    ) {

        val city = remember { mutableStateOf("") }
        TextField(
            value = city.value,
            onValueChange = { value ->
                city.value = value
            },
            leadingIcon = {
                val searchIcon = painterResource(R.drawable.ic_searchd)
                Icon(searchIcon, stringResource(id = R.string.description_search))
            },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primaryVariant
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.search_recent),
            style = typography.h3,
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Divider(
            color = MaterialTheme.colors.secondary,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        val recents = listOf(
            City("Coimbra, Portugal", "40.2089113", "-8.4263396"),
            City("Mountain View, California, US", "37.4134508", "-122.1513074"),
            City("London, United Kingdom", "51.5287352", "-0.3817816"),
            City("Lisboa, Portugal", "38.7437396", "-9.2302436")
        )

        LazyColumn {

            itemsIndexed(recents) { _, value ->

                Text(
                    text = value.name,
                    style = typography.h3,
                    modifier = Modifier.clickable {
                        viewModel.getWeather(value, units)
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
