package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sergeykuchin.spacexrockets.R
import kotlinx.android.synthetic.main.holder_launch_chart.view.*

class LaunchChartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(launchChartItem: LaunchChartItem?) {
        if (launchChartItem == null) return

        itemView.launch_chart.setBottomTextList(launchChartItem.bottomYearsList)
        itemView.launch_chart.setDrawDotLine(true)
        itemView.launch_chart.showPopup = true
        itemView.launch_chart.setColorArray(intArrayOf(ContextCompat.getColor(itemView.context, R.color.primaryColor)))
        itemView.launch_chart.setDataList(arrayListOf(launchChartItem.dataList))
    }
}