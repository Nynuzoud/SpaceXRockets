package com.example.sergeykuchin.spacexrockets.ui.greetingdialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.sergeykuchin.spacexrockets.R

class GreetingDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(R.string.greeting_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                this.dismiss()
            }

        return builder.create()
    }
}