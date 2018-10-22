package com.example.sergeykuchin.spacexrockets.other.errorhandler

import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.other.livedata.SingleLiveData
import com.example.sergeykuchin.spacexrockets.other.exceptions.EmptyListException
import java.net.UnknownHostException

class SimpleErrorHandlerImpl : SimpleErrorHandler {

    override fun handleCommonErrors(throwable: Throwable, errorsLiveData: SingleLiveData<Int>) {

        when (throwable) {
            is UnknownHostException -> errorsLiveData.value = R.string.network_error
            is EmptyListException -> {
                throwable.printStackTrace()
            }
            else -> {
                throwable.printStackTrace()
                errorsLiveData.value = R.string.unknown_error
            }
        }
    }
}