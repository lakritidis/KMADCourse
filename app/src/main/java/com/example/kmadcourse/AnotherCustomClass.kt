package com.example.kmadcourse

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AnotherCustomClass() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        // The dialog content
        builder.setMessage("Here we write our message - Do you accept my terms?")
        builder.setTitle("DIALOG TITLE")

        // Positive button
        builder.setPositiveButton("I accept") { dialog, id ->
            println("=== ACCEPTED")
        }

        // Negative button
        builder.setNegativeButton("I do not accept") { dialog, id ->
            println(" ==== CANCELLED")
        }
        return builder.create()
    }
}