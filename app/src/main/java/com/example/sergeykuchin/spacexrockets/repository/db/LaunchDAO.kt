package com.example.sergeykuchin.spacexrockets.repository.db

import androidx.room.Dao
import androidx.room.Query
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import io.reactivex.Single

@Dao
abstract class LaunchDAO : CommonDAO<Launch>() {

    @Query("SELECT * FROM launch WHERE rocketId=:rocketId")
    abstract fun getAllLaunchesByRocketId(rocketId: String): Single<List<Launch>>
}