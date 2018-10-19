package com.example.sergeykuchin.spacexrockets.ui.rocket

import androidx.lifecycle.MutableLiveData
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.other.errorhandler.SimpleErrorHandler
import com.example.sergeykuchin.spacexrockets.repository.api.launch.LaunchRepository
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepository
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.viewmodel.BaseViewModel
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
    val launchLiveData = MutableLiveData<Map<String, List<Launch>>>()

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

    override fun onCleared() {
        super.onCleared()
        clearSubscriptions()
    }
}