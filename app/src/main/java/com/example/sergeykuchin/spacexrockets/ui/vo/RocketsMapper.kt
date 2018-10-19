package com.example.sergeykuchin.spacexrockets.ui.vo

import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO
import io.reactivex.functions.Function

class RocketsMapper : Function<List<RocketDTO>, List<Rocket>> {

    override fun apply(t: List<RocketDTO>): List<Rocket> {
        val rocketMapper = RocketMapper()
        return t.mapNotNull { rocketMapper.apply(it) }
    }
}