package com.example.sergeykuchin.spacexrockets.ui.rockets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Fade
import com.example.sergeykuchin.spacexrockets.R
import com.example.sergeykuchin.spacexrockets.di.ComponentsHolder
import com.example.sergeykuchin.spacexrockets.other.kotlinextensions.showSnackbar
import com.example.sergeykuchin.spacexrockets.ui.rocket.RocketFragment
import com.example.sergeykuchin.spacexrockets.ui.rockets.adapter.RocketAdapter
import com.example.sergeykuchin.spacexrockets.ui.rockets.adapter.RocketAdapterListener
import com.example.sergeykuchin.spacexrockets.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_appbar.*
import kotlinx.android.synthetic.main.rockets_fragment.*
import javax.inject.Inject

class RocketsFragment : Fragment() {

    companion object {
        fun newInstance() = RocketsFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RocketsViewModel

    lateinit var rocketAdapter: RocketAdapter
    private val rocketAdapterListener = RocketAdapterListenerImpl()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.rockets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ComponentsHolder.applicationComponent.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RocketsViewModel::class.java)

        //there are two ways of implementing toolbar
        // 1. Implement and change MainActivity's actionBar
        // 2. Create custom toolbar for every fragment
        //I chose the second way because we have two different fragments with different toolbars,
        //so it will be rather to use separated toolbars
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.menu_rockets)
        toolbar.menu.findItem(R.id.action_active).isChecked = viewModel.activeOnly
        toolbar.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.action_active -> {
                    item.isChecked = !item.isChecked
                    viewModel.activeOnly = item.isChecked
                }
            }

            return@setOnMenuItemClickListener false
        }

        rocketAdapter = RocketAdapter()
        rocketAdapter.listener = rocketAdapterListener
        rocketAdapter.setHasStableIds(true)
        rockets_recycler.adapter = rocketAdapter

        loading.visibility = View.VISIBLE

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            viewModel.getRockets()
        }

        viewModel.rocketsLiveData.observe(this, Observer {
            rocketAdapter.data = it
            loading.visibility = View.GONE
            swipe_refresh.isRefreshing = false
        })

        viewModel.errorsLiveData.observe(this, Observer {
            root.showSnackbar(it, R.string.retry, Snackbar.LENGTH_INDEFINITE) {
                viewModel.getRockets()
            }
            loading.visibility = View.GONE
        })

        exitTransition = Fade()
    }

    private inner class RocketAdapterListenerImpl : RocketAdapterListener {
        override fun onItemClick(rocketId: String, image: ImageView) {
            fragmentManager
                ?.beginTransaction()
                ?.addSharedElement(image, ViewCompat.getTransitionName(image) ?: rocketId)
                ?.addToBackStack(RocketFragment::class.java.simpleName)
                ?.replace(R.id.container, RocketFragment.newInstance(rocketId), RocketFragment::class.java.simpleName)
                ?.commit()
        }
    }
}
