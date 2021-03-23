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
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.UNITS
import com.example.androiddevchallenge.domain.model.City
import com.example.androiddevchallenge.presentation.WeatherViewModel
import com.example.androiddevchallenge.ui.MainScreen
import com.example.androiddevchallenge.ui.OPTIONS
import com.example.androiddevchallenge.ui.SearchScreen
import com.example.androiddevchallenge.ui.SettingsScreen
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

private val DEFAULT_CITY = City("Coimbra, PT", "40.2089113", "-8.4263396")
private lateinit var bottomSheetOption: MutableState<OPTIONS>

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val units = remember { mutableStateOf(UNITS.METRIC) }
            viewModel.getWeather(DEFAULT_CITY, units.value)

            val isDarkThemeActive = isSystemInDarkTheme()
            val darkTheme = remember { mutableStateOf(isDarkThemeActive) }

            MyTheme(window, darkTheme.value) {

                bottomSheetOption = remember { mutableStateOf(OPTIONS.SEARCH) }

                MyApp(viewModel, units, darkTheme)
            }
        }
    }
}

// Start building your app here!
@ExperimentalMaterialApi
@Composable
fun MyApp(viewModel: WeatherViewModel,
          units: MutableState<UNITS>,
          darkTheme: MutableState<Boolean>) {

    Log.d(TAG, "MyApp")

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )

    BottomSheetScaffold(
        sheetContent = {
            if (bottomSheetOption.value == OPTIONS.SEARCH) {
                SearchScreen(viewModel, units.value, bottomSheetScaffoldState)
            } else {
                SettingsScreen(units, darkTheme)
            }
        },
        sheetBackgroundColor = MaterialTheme.colors.primary,
        scaffoldState = bottomSheetScaffoldState, sheetPeekHeight = 0.dp) {

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){

                    val settingsIcon = painterResource(R.drawable.ic_settingsd)
                    val settingsDescription = stringResource(id = R.string.description_settings)

                    Icon(
                        painter = settingsIcon,
                        contentDescription = settingsDescription,
                        modifier = Modifier
                            .width(25.dp)
                            .clickable {
                                bottomSheetOption.value = OPTIONS.SETTINGS

                                @Suppress("DEPRECATION")
                                Handler().postDelayed({
                                    GlobalScope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                            Log.d(TAG, "Opening settings")
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        } else {
                                            Log.d(TAG, "Collapsing settings")
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                }, 250)
                            }
                    )

                    Text(
                        text = "Coimbra, PT",
                        style = typography.h6
                    )

                    val searchIcon = painterResource(R.drawable.ic_searchd)
                    val searchDescription = stringResource(id = R.string.description_search)

                    Icon(
                        painter = searchIcon,
                        contentDescription = searchDescription,
                        modifier = Modifier
                            .width(25.dp)
                            .clickable {
                                bottomSheetOption.value = OPTIONS.SEARCH

                                @Suppress("DEPRECATION")
                                Handler().postDelayed({
                                    GlobalScope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        } else {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                }, 250)
                            }
                    )
                }
            },
            content = {
                MainScreen(viewModel, darkTheme.value, units.value)
            }
        )
    }
}