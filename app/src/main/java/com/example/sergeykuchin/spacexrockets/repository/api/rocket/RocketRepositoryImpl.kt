package com.example.sergeykuchin.spacexrockets.repository.api.rocket

import androidx.annotation.VisibleForTesting
import com.example.sergeykuchin.spacexrockets.other.exceptions.EmptyListException
import com.example.sergeykuchin.spacexrockets.repository.api.Api
import com.example.sergeykuchin.spacexrockets.repository.db.RocketDAO
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.ui.vo.RocketsMapper
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Repository that handles [RocketDTO]
 */
class RocketRepositoryImpl(
    private val api: Api,
    private val rocketDAO: RocketDAO
) : RocketRepository {

    /**
     * Loading data from DB first and then from API
     */
    override fun getAllRockets(activeOnly: Boolean): Flowable<List<Rocket>> {
        return Single.concat(
            getRocketsFromDb(activeOnly),
            getAllRocketsFromApi(activeOnly)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Getting all rockets from server, mapping into [Rocket] and saving to DB
     * If list is empty provides [EmptyListException]
     */
    @VisibleForTesting
    fun getAllRocketsFromApi(activeOnly: Boolean): Single<List<Rocket>> {
        return api
            .getAllRockets()
            .map(RocketsMapper())
            .flatMap { rockets ->
                if (rockets.isNotEmpty()) {
                    if (!activeOnly) {
                        Single.just(rockets)
                    } else {
                        Single.just(rockets.filter { it.active })
                    }
                } else Single.error(EmptyListException("List is empty"))
            }
            .doOnSuccess {
                rocketDAO.insert(*it.toTypedArray())
            }
    }

    private fun getRocketsFromDb(activeOnly: Boolean): Single<List<Rocket>> {
        return if (activeOnly) {
            rocketDAO.getActiveRockets()
        } else {
            rocketDAO.getAllRockets()
        }
    }

    /**
     * We do not need to update rocket's data so often,
     * so that's why we're getting data from DB only
     */
    override fun getRocket(rocketId: String): Single<Rocket> {

        return rocketDAO
            .getRocket(rocketId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}