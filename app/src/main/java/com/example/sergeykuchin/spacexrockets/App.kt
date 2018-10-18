package com.example.sergeykuchin.spacexrockets

import android.app.Application
import com.example.sergeykuchin.spacexrockets.di.ComponentsHolder
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initDagger()
    }

    private fun initTimber() {
        Timber.plant()
    }

    private fun initDagger() {
        ComponentsHolder.init(this)
    }
}