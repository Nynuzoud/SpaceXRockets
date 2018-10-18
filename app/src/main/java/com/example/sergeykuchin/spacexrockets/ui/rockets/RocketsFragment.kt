package com.example.sergeykuchin.spacexrockets.ui.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sergeykuchin.spacexrockets.R

class RocketsFragment : Fragment() {

    companion object {
        fun newInstance() = RocketsFragment()
    }

    private lateinit var viewModel: RocketsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.rockets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RocketsViewModel::class.java)

    }

}
