package com.example.sergeykuchin.spacexrockets.repository.api.launch

import androidx.annotation.VisibleForTesting
import com.example.sergeykuchin.spacexrockets.other.DateFormatter
import com.example.sergeykuchin.spacexrockets.other.exceptions.EmptyListException
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.repository.api.DataWrapper
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
    override fun getAllLaunches(rocketId: String): Flowable<DataWrapper<List<Launch>>> {
        return Single.concat(
            getAllLaunchesForRocketFromDb(rocketId),
            getAllLaunchesFromApi(rocketId)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getAllLaunchesForRocketFromDb(rocketId: String): Single<DataWrapper<List<Launch>>> {
        return launchDAO
            .getAllLaunchesByRocketId(rocketId)
            .flatMap {
                Single.just(
                    DataWrapper(
                        data = it,
                        error = null
                    )
                )
            }
            .onErrorResumeNext {
                Single.just(
                    DataWrapper<List<Launch>>(
                        data = null,
                        error = it
                    )
                )
            }
    }

    /**
     * Getting all launches from server, mapping into [Launch] and saving to DB
     * If list is empty provides [EmptyListException]
     */
    @VisibleForTesting
    fun getAllLaunchesFromApi(rocketId: String): Single<DataWrapper<List<Launch>>> {
        return api
            .getAllLaunches()
            .map(LaunchesMapper(DateFormatter()))
            .flatMap {
                if (it.isEmpty()) {
                    Single.error(EmptyListException("List is empty"))
                } else Single.just(it)
            }
            .flatMap { launches ->
                launches.filter { it.rocketId == rocketId }.let { Single.just(it) }
            }
            .flatMap {
                Single.just(
                    DataWrapper(
                        data = it,
                        error = null
                    )
                )
            }
            .doOnSuccess {
                launchDAO.insert(*it.data?.toTypedArray() ?: return@doOnSuccess )
            }
            .onErrorResumeNext {
                Single.just(
                    DataWrapper<List<Launch>>(
                        data = null,
                        error = it
                    )
                )
            }
    }
}