package com.example.sergeykuchin.spacexrockets.ui.rocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.other.errorhandler.SimpleErrorHandler
import com.example.sergeykuchin.spacexrockets.other.livedata.TwoSourcesLiveData
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchRepository
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepository
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterItemType
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterWrapper
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchChartItem
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.viewmodel.BaseViewModel
import javax.inject.Inject

class RocketViewModel @Inject constructor(
    applicationComponent: AppComponent,
    private val rocketRepository: RocketRepository,
    private val launchRepository: LaunchRepository,
    private val simpleErrorHandler: SimpleErrorHandler
) : BaseViewModel() {

    private var _rocketId: String? = null
    var rocketId: String?
        get() = _rocketId
        set(value) {
            _rocketId = value
            updateData()
        }

    val rocketLiveData = MutableLiveData<Rocket>()
    private val launchLiveData = MutableLiveData<Map<String, List<Launch>>>()
    private val twoSourcesLiveData =
        TwoSourcesLiveData<Rocket, Map<String, List<Launch>>>(
            rocketLiveData,
            launchLiveData
        )
    val launchAdapterWrapperLiveData: LiveData<List<LaunchAdapterWrapper>> =
        Transformations.switchMap(twoSourcesLiveData) {
            createLaunchAdapterWrapperLiveData(it.first, it.second)
        }

    init {
        applicationComponent.inject(this)
    }

    fun updateData() {
        getRocket(_rocketId)
        getLaunch(_rocketId)
    }

    private fun getRocket(rocketId: String?) {
        if (rocketId == null) return

        subscribe(rocketRepository
            .getRocket(rocketId)
            .subscribe({
                if (it != null) rocketLiveData.value = it
            }, {
                simpleErrorHandler.handleCommonErrors(it, errorsLiveData)
            })
        )
    }

    private fun getLaunch(rocketId: String?) {
        if (rocketId == null) return

        subscribe(launchRepository
            .getAllLaunches(rocketId)
            .subscribe({ launches ->
                if (launches.isNotEmpty()) {
                    launches
                        .groupBy { it.year }
                        .let { launchLiveData.value = it }
                }
            }, {
                simpleErrorHandler.handleCommonErrors(it, errorsLiveData)
            })
        )
    }

    private fun createLaunchAdapterWrapperLiveData(
        rocket: Rocket?,
        launchesGroupByYear: Map<String, List<Launch>>?
    ): LiveData<List<LaunchAdapterWrapper>> {

        //creating result list
        val list = ArrayList<LaunchAdapterWrapper>()
        var id = 0L

        //creating values but not add'em to list because we need special order
        val bottomYearsList = ArrayList<String>()
        val dataList = ArrayList<Int>()
        val tempList = ArrayList<LaunchAdapterWrapper>()

        launchesGroupByYear?.entries
            ?.forEach { launchesByYear ->
                bottomYearsList.add(launchesByYear.key)
                dataList.add(launchesByYear.value.size)
                val launchAdapterWrapperHeader = LaunchAdapterWrapper(
                    id = ++id,
                    year = launchesByYear.key,
                    itemType = LaunchAdapterItemType.HEADER
                )
                tempList.add(launchAdapterWrapperHeader)
                tempList.addAll(launchesByYear.value.map {
                    return@map LaunchAdapterWrapper(
                        id = ++id,
                        launch = it,
                        year = it.year,
                        itemType = LaunchAdapterItemType.LAUNCH
                    )
                })
            }

        //adding created values in special order
        //launch chart first
        list.add(
            LaunchAdapterWrapper(
                id = ++id,
                launchChartItem = LaunchChartItem(
                    bottomYearsList,
                    dataList
                ),
                itemType = LaunchAdapterItemType.LAUNCH_CHART
            )
        )

        //then description
        val description = LaunchAdapterWrapper(
            id = ++id,
            description = rocket?.description,
            itemType = LaunchAdapterItemType.DESCRIPTION
        )
        list.add(description)

        //and launch list
        list.addAll(tempList)

        //return result as a liveData
        val liveData = MutableLiveData<List<LaunchAdapterWrapper>>()
        liveData.value = list
        return liveData
    }

    override fun onCleared() {
        super.onCleared()
        clearSubscriptions()
    }
}