package com.example.sergeykuchin.spacexrockets.ui.rockets

import androidx.lifecycle.MutableLiveData
import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.di.components.AppComponent
import com.example.sergeykuchin.spacexrockets.other.exceptions.EmptyListException
import com.example.sergeykuchin.spacexrockets.repository.api.rocket.RocketRepository
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.viewmodel.BaseViewModel
import java.net.UnknownHostException
import javax.inject.Inject

class RocketsViewModel @Inject constructor(applicationComponent: AppComponent,
                                           private val rocketRepository: RocketRepository) : BaseViewModel() {

    val rocketsLiveData = MutableLiveData<MutableList<Rocket>>()
    val errorsLiveData = MutableLiveData<Int>()

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
                when (it) {
                    is UnknownHostException -> errorsLiveData.value = R.string.network_error
                    is EmptyListException -> {} //do nothing
                    else -> errorsLiveData.value = R.string.unknown_error
                }
            }))
    }

    override fun onCleared() {
        super.onCleared()
        clearSubscriptions()
    }
}
