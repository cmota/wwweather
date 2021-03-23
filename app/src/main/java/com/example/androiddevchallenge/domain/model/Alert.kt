package com.example.androiddevchallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int
)