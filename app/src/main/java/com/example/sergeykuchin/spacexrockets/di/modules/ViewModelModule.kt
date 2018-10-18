package com.example.sergeykuchin.spacexrockets.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sergeykuchin.spacexrockets.di.ViewModelKey
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsFragment
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsViewModel
import com.example.sergeykuchin.spacexrockets.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RocketsViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: RocketsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}