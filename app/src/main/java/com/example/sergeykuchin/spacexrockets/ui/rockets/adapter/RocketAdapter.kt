package com.example.sergeykuchin.spacexrockets.ui.rockets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.sergeykuchin.spacexrockets.databinding.HolderRocketBinding
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket

class RocketAdapter : RecyclerView.Adapter<RocketViewHolder>() {

    private val differ = AsyncListDiffer(this, RocketDiffUtilCallback())

    var data: List<Rocket>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    var listener: RocketAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val binding = HolderRocketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemId(position: Int): Long = differ.currentList[position].id
}