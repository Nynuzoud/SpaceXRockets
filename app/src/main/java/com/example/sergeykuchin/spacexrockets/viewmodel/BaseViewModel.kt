package com.example.sergeykuchin.spacexrockets.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sergeykuchin.spacexrockets.other.livedata.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    val errorsLiveData = SingleLiveData<Int>()

    private val subscriptions = CompositeDisposable()

    protected fun subscribe(disposable: Disposable?) {
        if (disposable != null) {
            subscriptions.add(disposable)
        }
    }

    protected fun unsubscribe(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            subscriptions.remove(disposable)
        }
    }

    protected fun clearSubscriptions() {
        subscriptions.clear()
    }
}