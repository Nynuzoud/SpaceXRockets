package com.example.sergeykuchin.spacexrockets.ui.rockets.adapter

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sergeykuchin.spacexrockets.databinding.HolderRocketBinding
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket

class RocketViewHolder(
    private val binding: HolderRocketBinding,
    private val listener: RocketAdapterListener? = null) : RecyclerView.ViewHolder(binding.root) {

    fun bind(rocket: Rocket) {

        binding.rocket = rocket
        ViewCompat.setTransitionName(binding.image, rocket.rocketId)
        binding.root.setOnClickListener {
            listener?.onItemClick(rocket.rocketId, binding.image)
        }
    }
}