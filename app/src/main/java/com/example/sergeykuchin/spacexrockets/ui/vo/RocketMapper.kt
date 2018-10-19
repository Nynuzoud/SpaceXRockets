package com.example.sergeykuchin.spacexrockets.ui.vo

import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO
import io.reactivex.functions.Function

class RocketMapper : Function<RocketDTO, Rocket> {

    override fun apply(t: RocketDTO): Rocket? {
        return Rocket(
            rocketId = t.rocket_id,
            id = t.id ?: return null,
            rocketName = t.rocket_name,
            country = t.country,
            enginesNumber = t.engines?.number ?: 0,
            active = t.active,
            imageUrl = t.flickr_images[0]
        )
    }
}