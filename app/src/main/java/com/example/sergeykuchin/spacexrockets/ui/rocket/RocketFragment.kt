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
import com.example.sergeykuchin.spacexrockets.other.errorhandler.ErrorViewImpl
import com.example.sergeykuchin.spacexrockets.other.kotlinextensions.showSnackbar
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchAdapterWrapper
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchHeaderItemDecoration
import com.example.sergeykuchin.spacexrockets.ui.rocket.adapter.LaunchesAdapter
import com.example.sergeykuchin.spacexrockets.ui.vo.Rocket
import com.example.sergeykuchin.spacexrockets.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rocket_fragment.*
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

    private val errorView = ErrorViewImpl()

    private lateinit var adapter: LaunchesAdapter

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

        loading.visibility = View.VISIBLE

        //there are two ways of implementing toolbar
        // 1. Implement and change MainActivity's actionBar
        // 2. Create custom toolbar for every fragment
        //I chose the second way because we have two different fragments with different toolbars,
        //so it will be rather to use separated toolbars
        rocket_toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_arrow_back_white_24dp)
        rocket_toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        setupRecycler()

        viewModel.rocketLiveData.observe(this, Observer {
            binding.rocket = it
            setupMainImage(it)
            errorView.removeAllSnackbarErrors()
        })

        viewModel.launchAdapterWrapperLiveData.observe(this, Observer {
            adapter.data = it as ArrayList<LaunchAdapterWrapper>
            loading.visibility = View.GONE
            errorView.removeAllSnackbarErrors()
        })

        viewModel.errorsLiveData.observe(this, Observer {
            errorView.addSnackBarError(root.showSnackbar(it!!, R.string.retry, Snackbar.LENGTH_INDEFINITE) {
                viewModel.updateData()
            })
            loading.visibility = View.GONE
        })

        val rocketId = arguments?.getString(ROCKET_ID)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            main_image.transitionName = rocketId
        }
        viewModel.rocketId = rocketId
    }

    private fun setupMainImage(rocket: Rocket) {
        picasso
            .load(rocket.imageUrl)
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
    }

    private fun setupRecycler() {
        adapter = LaunchesAdapter()
        adapter.setHasStableIds(true)
        launches_recycler.adapter = adapter
        launches_recycler.addItemDecoration(LaunchHeaderItemDecoration(adapter))
    }
}
