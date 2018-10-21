package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.sergeykuchin.spacexrockets.databinding.HolderLaunchBinding
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch

class LaunchViewHolder(private val binding: HolderLaunchBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launch: Launch?) {
        if (launch == null) return

        binding.launch = launch
    }
}