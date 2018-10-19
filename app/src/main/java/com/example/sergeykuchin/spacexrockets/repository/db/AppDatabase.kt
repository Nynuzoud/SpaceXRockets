package com.example.sergeykuchin.spacexrockets.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket

@Database(
    entities = [
        Rocket::class,
        Launch::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rocketDao(): RocketDAO

    abstract fun launchDao(): LaunchDAO

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "appDatabase-spacexrockets")
                .build()
    }
}