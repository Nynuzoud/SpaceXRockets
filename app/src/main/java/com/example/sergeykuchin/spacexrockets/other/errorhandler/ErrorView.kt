package com.example.sergeykuchin.spacexrockets.other.errorhandler

import com.google.android.material.snackbar.Snackbar

interface ErrorView {

    fun addSnackBarError(snackbar: Snackbar)

    fun removeAllSnackbarErrors()
}