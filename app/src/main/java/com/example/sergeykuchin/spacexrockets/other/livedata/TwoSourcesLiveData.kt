package com.example.sergeykuchin.spacexrockets.other.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class TwoSourcesLiveData<O, T>(oneSource: LiveData<O>, twoSource: LiveData<T>) : MediatorLiveData<Pair<O?, T?>>() {

    init {
        addSource(oneSource) { first ->
            value = Pair(first, twoSource.value)
        }
        addSource(twoSource) { second ->
            value = Pair(oneSource.value, second)
        }
    }
}