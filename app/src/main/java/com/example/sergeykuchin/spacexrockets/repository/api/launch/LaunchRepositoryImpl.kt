package com.example.sergeykuchin.spacexrockets.repository.api.launch

import androidx.annotation.VisibleForTesting
import com.example.sergeykuchin.spacexrockets.other.DateFormatter
import com.example.sergeykuchin.spacexrockets.other.exceptions.EmptyListException
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.repository.db.LaunchDAO
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.ui.vo.LaunchesMapper
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Repository that handles [LaunchDTO]
 */
class LaunchRepositoryImpl(
    private val api: Api,
    private val launchDAO: LaunchDAO
) : LaunchRepository {

    /**
     * Loading data from DB first and then from API
     * @return filtered launches for rocket by [rocketId]
     */
    override fun getAllLaunches(rocketId: String): Flowable<List<Launch>> {
        return Single.concat(
            getAllLaunchesForRocketFromDb(rocketId),
            getAllLaunchesFromApi()
                .flatMap { launches ->
                    launches.filter { it.rocketId == rocketId }.let { Single.just(it) }
                }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getAllLaunchesForRocketFromDb(rocketId: String): Single<List<Launch>> {
        return launchDAO
            .getAllLaunchesByRocketId(rocketId)
    }

    /**
     * Getting all launches from server, mapping into [Launch] and saving to DB
     * If list is empty provides [EmptyListException]
     */
    @VisibleForTesting
    fun getAllLaunchesFromApi(): Single<List<Launch>> {
        return api
            .getAllLaunches()
            .map(LaunchesMapper(DateFormatter()))
            .flatMap {
                if (it.isNotEmpty()) {
                    launchDAO.insert(*it.toTypedArray())
                    Single.just(it)
                } else Single.error(EmptyListException("List is empty"))
            }
    }
}