package com.example.sergeykuchin.spacexrockets.other.preferences

import android.content.Context

class PreferencesImpl(val context: Context) : Preferences {

    private val PREFS = "PREFS"
    private val PREFS_FIRST_LAUNCH = "PREFS_FIRST_LAUNCH"

    private fun getPrefs() = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun setFirstLaunch(isFirstLaunch: Boolean) {
        getPrefs().edit().putBoolean(PREFS_FIRST_LAUNCH, isFirstLaunch).apply()
    }

    override fun getFirstLaunch(): Boolean = getPrefs().getBoolean(PREFS_FIRST_LAUNCH, true)
}