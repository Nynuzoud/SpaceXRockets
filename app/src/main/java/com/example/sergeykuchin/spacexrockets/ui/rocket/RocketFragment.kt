package com.example.sergeykuchin.spacexrockets.ui.rocket

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.databinding.RocketFragmentBinding
import com.example.sergeykuchin.spacexrockets.di.ComponentsHolder
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterItemType
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterWrapper
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchHeaderItemDecoration
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchesAdapter
import com.example.sergeykuchin.spacexrockets.ui.vo.Launch
import com.example.sergeykuchin.spacexrockets.viewmodel.ViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rocket_fragment.*
import timber.log.Timber
import javax.inject.Inject


class RocketFragment : Fragment() {

    companion object {

        private const val ROCKET_ID = "rocket_id"

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

    @Inject
    lateinit var picasso: Picasso

    private lateinit var viewModel: RocketViewModel
    private lateinit var binding: RocketFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

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

        setupMainImage()

        viewModel.launchLiveData.observe(this, Observer {
            setupLaunchChart(it)
            setupLaunchHistory(it)
        })

        val rocketId = arguments?.getString(ROCKET_ID)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            main_image.transitionName = rocketId
        }
        viewModel.rocketId = rocketId
    }

    private fun setupMainImage() {
        viewModel.rocketLiveData.observe(this, Observer {
            binding.rocket = it
            picasso
                .load(it.imageUrl)
                .fit()
                .noFade()
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(main_image, object : Callback {
                    override fun onSuccess() {
                        startPostponedEnterTransition()
                    }

                    override fun onError(e: Exception?) {
                        startPostponedEnterTransition()
                    }
                })
        })
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

    private fun setupLaunchHistory(launches: Map<String, List<Launch>>) {
        val launchesList = ArrayList<LaunchAdapterWrapper>()

        var id = 0L
        launches.entries
            .forEach { launchesByYear ->
                val launchAdapterWrapperHeader = LaunchAdapterWrapper(
                    id = ++id,
                    year = launchesByYear.key,
                    itemType = LaunchAdapterItemType.HEADER
                )
                launchesList.add(launchAdapterWrapperHeader)
                launchesList.addAll(launchesByYear.value.map {
                    return@map LaunchAdapterWrapper(
                        id = ++id,
                        launch = it,
                        year = it.year,
                        itemType = LaunchAdapterItemType.LAUNCH
                    )
                })
            }
        val launchesAdapter = LaunchesAdapter()
        launchesAdapter.setHasStableIds(false)
        launches_recycler.adapter = launchesAdapter
        launches_recycler.addItemDecoration(LaunchHeaderItemDecoration(launchesAdapter))
        launchesAdapter.data = launchesList
    }
}
