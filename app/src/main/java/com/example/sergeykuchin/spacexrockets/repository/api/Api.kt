package com.example.sergeykuchin.spacexrockets.repository.api

import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchDTO
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketDTO
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("rockets")
    fun getAllRockets(): Single<MutableList<RocketDTO>>

    @GET("launches")
    fun getAllLaunches(): Single<MutableList<LaunchDTO>>
}