package com.example.sergeykuchin.spacexrockets.other.kotlinextensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(@StringRes message: Int, @StringRes buttonText: Int, length: Int, function: () -> Unit) {
    val snackbar = Snackbar
        .make(this, message, length)

    snackbar
        .setAction(buttonText) {
            function()
            snackbar.dismiss()
        }
        .show()
}