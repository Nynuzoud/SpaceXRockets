package com.example.sergeykuchin.spacexrockets.ui.rockets.adapter

import android.widget.ImageView

interface RocketAdapterListener {

    fun onItemClick(rocketId: String, image: ImageView)
}