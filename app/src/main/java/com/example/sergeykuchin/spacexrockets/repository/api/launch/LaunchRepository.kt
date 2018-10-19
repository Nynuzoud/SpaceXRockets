package com.example.sergeykuchin.spacexrockets.repository.api.launch

import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import io.reactivex.Flowable

interface LaunchRepository {

    fun getAllLaunches(rocketId: String): Flowable<MutableList<Launch>>
}