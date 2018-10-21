package com.example.sergeykuchin.spacexrockets.ui.rocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.other.errorhandler.SimpleErrorHandler
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchRepository
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepository
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterItemType
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterWrapper
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchChartItem
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class RocketViewModel @Inject constructor(applicationComponent: AppComponent,
                                          private val rocketRepository: RocketRepository,
                                          private val launchRepository: LaunchRepository,
                                          private val simpleErrorHandler: SimpleErrorHandler) : BaseViewModel() {

    private var _rocketId: String? = null
    var rocketId: String?
        get() = _rocketId
        set(value) {
            if (value == null) return
            _rocketId = value
            getRocket(value)
            getLaunch(value)
        }

    val rocketLiveData = MutableLiveData<Rocket>()
    private val launchLiveData = MutableLiveData<Map<String, List<Launch>>>()
    val launchAdapterWrapperLiveData: LiveData<List<LaunchAdapterWrapper>> = Transformations.map(launchLiveData) {
        createLaunchAdapterWrapperLiveData(rocketLiveData.value, it)
    }

    init {
        applicationComponent.inject(this)
    }

    private fun getRocket(rocketId: String) {
        subscribe(rocketRepository
            .getRocket(rocketId)
            .subscribe({
                rocketLiveData.value = it
            },{
                simpleErrorHandler.handleCommonErrors(it, errorsLiveData)
            }))
    }

    private fun getLaunch(rocketId: String) {
        subscribe(launchRepository
            .getAllLaunches(rocketId)
            .subscribe({ launches ->
                launches
                    .groupBy { it.year }
                    .let { launchLiveData.value = it }
            }, {
                simpleErrorHandler.handleCommonErrors(it, errorsLiveData)
            }))
    }

    private fun createLaunchAdapterWrapperLiveData(rocket: Rocket?, launchesGroupByYear: Map<String, List<Launch>>): List<LaunchAdapterWrapper> {
        val list = ArrayList<LaunchAdapterWrapper>()
        var id = 0L

        val launchChartType = LaunchAdapterWrapper(
            id = ++id,
            launchChartItem = createLaunchChartItem(launchesGroupByYear),
            itemType = LaunchAdapterItemType.LAUNCH_CHART
        )
        list.add(launchChartType)

        val description = LaunchAdapterWrapper(
            id = ++id,
            description = rocket?.description,
            itemType = LaunchAdapterItemType.DESCRIPTION
        )
        list.add(description)

        launchesGroupByYear.entries
            .forEach { launchesByYear ->
                val launchAdapterWrapperHeader = LaunchAdapterWrapper(
                    id = ++id,
                    year = launchesByYear.key,
                    itemType = LaunchAdapterItemType.HEADER
                )
                list.add(launchAdapterWrapperHeader)
                list.addAll(launchesByYear.value.map {
                    return@map LaunchAdapterWrapper(
                        id = ++id,
                        launch = it,
                        year = it.year,
                        itemType = LaunchAdapterItemType.LAUNCH
                    )
                })
            }
        return list
    }

    private fun createLaunchChartItem(launchesGroupByYear: Map<String, List<Launch>>): LaunchChartItem {
        val bottomYearsList = ArrayList<String>()
        val dataList = ArrayList<Int>()
        launchesGroupByYear.entries
            .forEach {
                try {
                    bottomYearsList.add(it.key)
                    dataList.add(it.value.size)
                } catch (e: NumberFormatException) {
                    Timber.d(e)
                }
            }
        return LaunchChartItem(
            bottomYearsList = bottomYearsList,
            dataList = dataList
        )
    }

    override fun onCleared() {
        super.onCleared()
        clearSubscriptions()
    }
}