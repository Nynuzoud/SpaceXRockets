package com.example.sergeykuchin.spacexrockets.di.databinding

import com.example.sergeykuchin.spacexrockets.other.databinding.ImageBindingAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class BindingModule {

    @Provides
    @DataBinding
    fun provideImageBindingAdapter(picasso: Picasso): ImageBindingAdapter = ImageBindingAdapter(picasso)
}