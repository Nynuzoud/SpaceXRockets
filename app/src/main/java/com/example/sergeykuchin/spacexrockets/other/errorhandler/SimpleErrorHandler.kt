package com.example.sergeykuchin.spacexrockets.other.errorhandler

import androidx.lifecycle.MutableLiveData

interface SimpleErrorHandler {

    fun handleCommonErrors(throwable: Throwable, errorsLiveData: MutableLiveData<Int>)
}