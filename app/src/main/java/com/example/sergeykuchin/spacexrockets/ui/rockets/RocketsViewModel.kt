package com.example.sergeykuchin.spacexrockets.ui.rockets

import androidx.lifecycle.MutableLiveData
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.other.errorhandler.SimpleErrorHandler
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepository
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.viewmodel.BaseViewModel
import javax.inject.Inject

class RocketsViewModel @Inject constructor(applicationComponent: AppComponent,
                                           private val rocketRepository: RocketRepository,
                                           private val simpleErrorHandler: SimpleErrorHandler) : BaseViewModel() {

    val rocketsLiveData = MutableLiveData<List<Rocket>>()

    private var _activeOnly: Boolean = false
    var activeOnly: Boolean
        get() = _activeOnly
        set(value) {
            _activeOnly = value
            getRockets()
        }

    init {
        applicationComponent.inject(this)

        getRockets()
    }

    fun getRockets() {

        subscribe(rocketRepository
            .getAllRockets(_activeOnly)
            .subscribe({
                rocketsLiveData.value = it
            }, {
                simpleErrorHandler.handleCommonErrors(it, errorsLiveData)
            }))
    }

    override fun onCleared() {
        super.onCleared()
        clearSubscriptions()
    }
}
