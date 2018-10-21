package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import com.example.sergeykuchin.spacexrockets.ui.vo.Launch

data class LaunchAdapterWrapper(

    val id: Long,
    val launch: Launch? = null,
    val year: String? = null,
    val launchChartItem: LaunchChartItem? = null,
    val description: String? = null,
    val itemType: LaunchAdapterItemType
)