package com.example.sergeykuchin.spacexrockets.ui.rockets.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket

class RocketDiffUtilCallback : DiffUtil.ItemCallback<Rocket>() {

    override fun areItemsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
        return oldItem.rocketId == newItem.rocketId
    }

    override fun areContentsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
        return oldItem == newItem
    }
}