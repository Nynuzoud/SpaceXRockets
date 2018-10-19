package com.example.sergeykuchin.spacexrockets.ui.rocket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.databinding.RocketFragmentBinding
import com.example.sergeykuchin.spacexrockets.di.ComponentsHolder
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.rocket_fragment.*
import timber.log.Timber
import javax.inject.Inject

class RocketFragment : Fragment() {

    companion object {

        private val ROCKET_ID = "rocket_id"

        fun newInstance(rocketId: String): RocketFragment {

            val bundle = Bundle()
            bundle.putString(ROCKET_ID, rocketId)

            val fragment = RocketFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RocketViewModel
    private lateinit var binding: RocketFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rocket_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ComponentsHolder.applicationComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RocketViewModel::class.java)

        //there are two ways of implementing toolbar
        // 1. Implement and change MainActivity's actionBar
        // 2. Create custom toolbar for every fragment
        //I chose the second way because we have two different fragments with different toolbars,
        //so it will be rather to use separated toolbars
        rocket_toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_arrow_back_white_24dp)
        rocket_toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        viewModel.rocketLiveData.observe(this, Observer {
            binding.rocket = it
        })

        viewModel.launchLiveData.observe(this, Observer {
            setupLaunchChart(it)
        })

        val rocketId = arguments?.getString(ROCKET_ID)
        viewModel.rocketId = rocketId
    }

    private fun setupLaunchChart(launches: Map<String, List<Launch>>) {
        if (launches.isEmpty()) {
            launches_per_year_title.visibility = View.GONE
            launch_chart.visibility = View.GONE
            return
        }

        val bottomYearsList = ArrayList<String>()
        val dataList = ArrayList<Int>()
        launches.entries
            .forEach {
                try {
                    bottomYearsList.add(it.key)
                    dataList.add(it.value.size)
                } catch (e: NumberFormatException) {
                    Timber.d(e)
                }
            }

        launch_chart.setBottomTextList(bottomYearsList)
        launch_chart.setDrawDotLine(true)
        launch_chart.showPopup = true
        launch_chart.setColorArray(intArrayOf(ContextCompat.getColor(context!!, R.color.primaryColor)))
        launch_chart.setDataList(arrayListOf(dataList))
    }
}
