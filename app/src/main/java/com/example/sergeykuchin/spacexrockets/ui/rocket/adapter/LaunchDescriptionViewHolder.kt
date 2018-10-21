package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.holder_launch_description.view.*

class LaunchDescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(descr: String?) {
        if (descr == null) return

        itemView.description.text = descr
    }
}