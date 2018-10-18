package com.example.sergeykuchin.spacexrockets.di

import android.app.Application
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.di.components.DaggerAppComponent
import com.example.sergeykuchin.spacexrockets.di.modules.AppModule

object ComponentsHolder {

    lateinit var applicationComponent: AppComponent

    fun init(application: Application) {

        applicationComponent = DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
    }
}