package com.example.sergeykuchin.spacexrockets

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.sergeykuchin.spacexrockets.di.ComponentsHolder
import com.example.sergeykuchin.spacexrockets.di.databinding.DaggerBindingComponent
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initDagger()
        initBindingComponent()
    }

    private fun initTimber() {
        Timber.plant()
    }

    private fun initDagger() {
        ComponentsHolder.init(this)
    }

    private fun initBindingComponent() {
        DataBindingUtil.setDefaultComponent(
            DaggerBindingComponent.builder()
                .appComponent(ComponentsHolder.applicationComponent)
                .build()
        )
    }
}