package com.example.sergeykuchin.spacexrockets.repository.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy

abstract class CommonDAO<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg obj: T): List<Long>
}