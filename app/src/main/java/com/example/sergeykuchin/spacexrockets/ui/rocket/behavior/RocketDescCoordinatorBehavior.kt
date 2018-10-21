package com.example.sergeykuchin.spacexrockets.ui.rocket.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import timber.log.Timber

class RocketDescCoordinatorBehavior(context: Context, attrs: AttributeSet?) : CoordinatorLayout.Behavior<NestedScrollView>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: NestedScrollView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: NestedScrollView, dependency: View): Boolean {
        Timber.d("DEPENDENCY HEIGHT: ${dependency.height}")
        return true
    }
}