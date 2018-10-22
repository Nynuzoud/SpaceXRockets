package com.example.sergeykuchin.spacexrockets.other.errorhandler

import com.google.android.material.snackbar.Snackbar

class ErrorViewImpl : ErrorView {

    private val errorList = ArrayList<Snackbar>()

    override fun addSnackBarError(snackbar: Snackbar) {
        errorList.add(snackbar)
    }

    override fun removeAllSnackbarErrors() {
        errorList.forEach {
            it.dismiss()
        }
        errorList.clear()
    }
}