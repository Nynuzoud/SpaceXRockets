package com.example.sergeykuchin.spacexrockets.ui.vo

import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO
import io.reactivex.functions.Function

class RocketsMapper : Function<MutableList<RocketDTO>, MutableList<Rocket>> {

    override fun apply(t: MutableList<RocketDTO>): MutableList<Rocket> {
        val rocketMapper = RocketMapper()
        return t
            .asSequence()
            .mapNotNull { rocketMapper.apply(it) }
            .toMutableList()
    }
}