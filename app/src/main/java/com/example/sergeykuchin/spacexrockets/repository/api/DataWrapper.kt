package com.example.sergeykuchin.spacexrockets.repository.api

data class DataWrapper<T>(

    val data: T?,
    val error: Throwable?
)