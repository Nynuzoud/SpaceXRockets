package com.example.sergeykuchin.spacexrockets.other.errorhandler

import com.example.sergeykuchin.spacexrockets.other.livedata.SingleLiveData

interface SimpleErrorHandler {

    fun handleCommonErrors(throwable: Throwable, errorsLiveData: SingleLiveData<Int>)
}