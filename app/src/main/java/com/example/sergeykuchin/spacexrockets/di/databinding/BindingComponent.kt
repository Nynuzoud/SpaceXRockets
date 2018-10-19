package com.example.sergeykuchin.spacexrockets.di.databinding

import androidx.databinding.DataBindingComponent
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.other.databinding.ImageBindingAdapter
import dagger.Component

@DataBinding
@Component(dependencies = [AppComponent::class],
    modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {

    override fun getImageBindingAdapter(): ImageBindingAdapter
}