package com.example.sergeykuchin.spacexrockets.ui.vo

import com.example.sergeykuchin.spacexrockets.other.DateFormatter
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchDTO
import io.reactivex.functions.Function

class LaunchesMapper(private val dateFormatter: DateFormatter) : Function<List<LaunchDTO>, List<Launch>> {

    override fun apply(t: List<LaunchDTO>): List<Launch> {

        val launchMapper = LaunchMapper(dateFormatter)
        return t.mapNotNull { launchMapper.apply(it) }
    }
}