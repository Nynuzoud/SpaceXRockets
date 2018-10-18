package com.example.sergeykuchin.spacexrockets.ui.vo

data class Launch(

    val flightNumber: Int,
    val rocketId: String,
    val missionName: String = "",
    val dateString: String = "",
    val isSuccessful: String = "",
    val patchSmallUrl: String = ""
)