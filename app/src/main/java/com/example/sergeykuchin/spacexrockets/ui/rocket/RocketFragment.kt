package com.example.sergeykuchin.spacexrockets.ui.rocket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.di.ComponentsHolder
import com.example.sergeykuchin.spacexrockets.viewmodel.ViewModelFactory
import javax.inject.Inject

class RocketFragment : Fragment() {

    companion object {
        fun newInstance() = RocketFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RocketViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rocket_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ComponentsHolder.applicationComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RocketViewModel::class.java)
    }
}
