package com.example.sergeykuchin.spacexrockets.di.components

import com.example.sergeykuchin.spacexrockets.di.modules.AppModule
import com.example.sergeykuchin.spacexrockets.ui.rocket.RocketFragment
import com.example.sergeykuchin.spacexrockets.ui.rocket.RocketViewModel
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsFragment
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsViewModel
import com.squareup.picasso.Picasso
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    val picasso: Picasso

    fun inject(rocketsFragment: RocketsFragment)
    fun inject(rocketsViewModel: RocketsViewModel)

    fun inject(rocketFragment: RocketFragment)
    fun inject(rocketViewModel: RocketViewModel)
}