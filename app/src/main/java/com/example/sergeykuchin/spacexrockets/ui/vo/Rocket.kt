package com.example.sergeykuchin.spacexrockets.ui.vo

data class Rocket(

    val rocketId: String,
    val rocketName: String = "",
    val country: String = "",
    val enginesNumber: Int,
    val active: Boolean,
    val imageUrl: String
)