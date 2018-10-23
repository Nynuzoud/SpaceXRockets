package com.example.sergeykuchin.spacexrockets.other.preferences

interface Preferences {

    fun setFirstLaunch(isFirstLaunch: Boolean)
    fun getFirstLaunch(): Boolean
}