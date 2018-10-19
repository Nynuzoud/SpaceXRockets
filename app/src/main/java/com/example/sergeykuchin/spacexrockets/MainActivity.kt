package com.example.sergeykuchin.spacexrockets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sergeykuchin.spacexrockets.ui.rockets.RocketsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RocketsFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStackImmediate()
        }
    }
}
