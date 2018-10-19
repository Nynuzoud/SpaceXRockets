package com.example.sergeykuchin.spacexrockets.ui.rockets.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.sergeykuchin.spacexrockets.databinding.RocketHolderBinding
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket

class RocketViewHolder(
    private val binding: RocketHolderBinding,
    private val listener: RocketAdapterListener? = null) : RecyclerView.ViewHolder(binding.root) {

    fun bind(rocket: Rocket) {

        binding.rocket = rocket
        binding.root.setOnClickListener { listener?.onItemClick(rocket.rocketId) }
    }
}