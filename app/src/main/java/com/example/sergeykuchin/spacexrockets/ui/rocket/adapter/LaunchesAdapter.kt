package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.databinding.HolderLaunchBinding

class LaunchesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), LaunchHeaderInterface {

    private var _data: ArrayList<LaunchAdapterWrapper> = ArrayList()
    var data: ArrayList<LaunchAdapterWrapper>
        get() = _data
        set(value) {
            _data = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LaunchAdapterItemType.HEADER.ordinal -> {
                LaunchHeaderViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.holder_launch_header, parent, false)
                )
            }
            LaunchAdapterItemType.LAUNCH.ordinal -> {
                val binding = HolderLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LaunchViewHolder(binding)
            }
            else -> {
                LaunchHeaderViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.holder_launch_header, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            LaunchAdapterItemType.HEADER.ordinal -> (holder as LaunchHeaderViewHolder).bind(data[position].year)
            LaunchAdapterItemType.LAUNCH.ordinal -> (holder as LaunchViewHolder).bind(data[position].launch)
        }
    }

    override fun getItemCount(): Int = _data.size

    override fun getItemId(position: Int): Long = _data[position].id

    override fun getItemViewType(position: Int): Int = _data[position].itemType.ordinal

    override fun getHeaderPositionForItem(itemPosition: Int): Int =
        (itemPosition downTo 0)
            .firstOrNull { _data[it].itemType == LaunchAdapterItemType.HEADER } ?: 0

    override fun getHeaderLayout(headerPosition: Int): Int = R.layout.holder_launch_header

    override fun bindHeaderData(header: View, headerPosition: Int) {
        (header as TextView).text = _data[headerPosition].year
    }

    override fun isHeader(itemPosition: Int): Boolean = _data[itemPosition].itemType == LaunchAdapterItemType.HEADER
}