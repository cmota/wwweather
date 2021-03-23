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
package com.example.androiddevchallenge.ui.theme

import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.example.androiddevchallenge.R

private const val TAG = "Theme"

val LightImages = listOf(
    R.drawable.ic_01d,
    R.drawable.ic_02d,
    R.drawable.ic_03d,
    R.drawable.ic_04d,
    R.drawable.ic_09d,
    R.drawable.ic_10d,
    R.drawable.ic_11d,
    R.drawable.ic_13d,
    R.drawable.ic_50d
)

val DarkImages = listOf(
    R.drawable.ic_01n,
    R.drawable.ic_02n,
    R.drawable.ic_03n,
    R.drawable.ic_04n,
    R.drawable.ic_09n,
    R.drawable.ic_10n,
    R.drawable.ic_11n,
    R.drawable.ic_13n,
    R.drawable.ic_50n
)

@Composable
fun MyTheme(window: Window, darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colors = if (darkTheme) {
        darkColors(
            primary = darkBackground,
            primaryVariant = darkSelected,
            secondary = darkTextSecondary,
            secondaryVariant = darkNotSelected,
            onPrimary = darkText,
            onSecondary = darkSingleOption,
            surface = darkBackgroundField
        )
    } else {
        lightColors(
            primary = lightBackground,
            primaryVariant = lightSelected,
            secondary = lightTextSecondary,
            secondaryVariant = lightNotSelected,
            onPrimary = lightText,
            onSecondary = lightSingleOption,
            surface = lightBackgroundField
        )
    }

    Log.d(TAG, "Current theme | dark=$darkTheme")

    window.statusBarColor = colors.primary.toArgb()
    window.navigationBarColor = colors.primary.toArgb()

    @Suppress("DEPRECATION")
    if (darkTheme) {
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
    } else {
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
