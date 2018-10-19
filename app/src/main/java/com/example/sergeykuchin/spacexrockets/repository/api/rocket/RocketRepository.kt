package com.example.sergeykuchin.spacexrockets.repository.api.rocket

import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import io.reactivex.Flowable
import io.reactivex.Single

interface RocketRepository {

    fun getAllRockets(activeOnly: Boolean): Flowable<List<Rocket>>

    fun getRocket(rocketId: String): Single<Rocket>
}