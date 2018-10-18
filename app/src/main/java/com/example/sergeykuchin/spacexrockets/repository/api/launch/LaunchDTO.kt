package com.example.sergeykuchin.spacexrockets.repository.api.launch

import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO

data class LaunchDTO (

    val flight_number: Int,
    val rocket: RocketDTO? = null,
    val mission_name: String = "",
    val launch_date_utc: String = "",
    val launch_success: Boolean? = null
)