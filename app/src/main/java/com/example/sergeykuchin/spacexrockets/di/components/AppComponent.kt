package com.example.sergeykuchin.spacexrockets.di.components

import android.app.Application
import com.example.sergeykuchin.spacexrockets.di.modules.AppModule
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.ui.rocket.RocketFragment
import com.example.sergeykuchin.spacexrockets.ui.rocket.RocketViewModel
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsFragment
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    val application: Application
    val api: Api

    fun inject(rocketsFragment: RocketsFragment)
    fun inject(rocketsViewModel: RocketsViewModel)

    fun inject(rocketFragment: RocketFragment)
    fun inject(rocketViewModel: RocketViewModel)
}