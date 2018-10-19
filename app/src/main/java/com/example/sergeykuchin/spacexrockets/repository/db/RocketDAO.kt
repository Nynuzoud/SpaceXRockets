package com.example.sergeykuchin.spacexrockets.repository.db

import androidx.room.Dao
import androidx.room.Query
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import io.reactivex.Single

/**
 * We're using [Single] here because lists are not very big
 */
@Dao
abstract class RocketDAO : CommonDAO<Rocket>() {

    @Query("SELECT * FROM rocket ORDER BY id ASC")
    abstract fun getAllRockets(): Single<MutableList<Rocket>>

    @Query("SELECT * FROM rocket WHERE rocketId=:rocketId ORDER BY id ASC")
    abstract fun getRocket(rocketId: String): Single<Rocket>

    @Query("SELECT * FROM rocket WHERE active=1 ORDER BY id ASC")
    abstract fun getActiveRockets(): Single<MutableList<Rocket>>
}