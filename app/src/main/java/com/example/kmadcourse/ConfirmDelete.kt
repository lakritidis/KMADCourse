package com.example.kmadcourse

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDelete() : DialogFragment() {

    private var pid: Int = 0

    constructor(productId: Int) : this() {
        pid = productId
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())

        // The dialog content
        builder.setMessage(R.string.confirmdelete)

        // Positive button
        builder.setPositiveButton(R.string.yes) { dialog, id ->
            println("Canceled")
        }

        // Negative button
        builder.setNegativeButton(R.string.no) { dialog, id ->
            println("Canceled")
        }
        return builder.create()
    }
}