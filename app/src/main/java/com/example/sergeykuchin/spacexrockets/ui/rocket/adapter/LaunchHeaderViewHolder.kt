package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LaunchHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(year: String?) {
        if (year == null) return

        (itemView as TextView).text = year
    }
}