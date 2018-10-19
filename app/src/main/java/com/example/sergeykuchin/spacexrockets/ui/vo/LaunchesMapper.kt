package com.example.sergeykuchin.spacexrockets.ui.vo

import com.example.sergeykuchin.spacexrockets.other.DateFormatter
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchDTO
import io.reactivex.functions.Function

class LaunchesMapper(private val dateFormatter: DateFormatter) : Function<MutableList<LaunchDTO>, MutableList<Launch>> {

    override fun apply(t: MutableList<LaunchDTO>): MutableList<Launch> {

        val launchMapper = LaunchMapper(dateFormatter)
        return t
            .asSequence()
            .mapNotNull { launchMapper.apply(it) }
            .toMutableList()
    }
}