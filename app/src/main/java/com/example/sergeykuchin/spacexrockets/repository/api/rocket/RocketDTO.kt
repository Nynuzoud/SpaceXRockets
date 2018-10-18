package com.example.sergeykuchin.spacexrockets.repository.api.rocket

data class RocketDTO(

    val rocket_id: String,
    val rocket_name: String = "",
    val country: String = "",
    val engines: EnginesDTO? = null,
    val active: Boolean = false,
    val flickr_images: MutableList<String> = ArrayList()
)