package com.example.androiddevchallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Temp(
    val day: Double = 0.0,
    val eve: Double = 0.0,
    val max: Double = 0.0,
    val min: Double = 0.0,
    val morn: Double = 0.0,
    val night: Double = 0.0
)