package com.example.sergeykuchin.spacexrockets.repository.api.rocket

import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import io.reactivex.Flowable

interface RocketRepository {

    fun getAllRockets(activeOnly: Boolean): Flowable<MutableList<Rocket>>
}