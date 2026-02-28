package com.example.kmadcourse

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.kmadcourse.databinding.ActivityMainBinding
import androidx.core.graphics.toColorInt

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Toast.makeText(this, "App started successfully!", Toast.LENGTH_LONG).show()

        val btnSubmit = binding.btnSubmit
        btnSubmit.setOnClickListener {
            Toast.makeText(this,"Button Pressed",Toast.LENGTH_LONG).show()
        }

        val et = binding.editSurname
        et.setText(R.string.cap_hello)
        // et.setEnabled(false)
        et.visibility = View.VISIBLE
        et.textSize = 20.0F
        // et.setTextColor(Color.parseColor("#bb2539"))
        et.setTextColor("#bb2539".toColorInt())
        et.setBackgroundColor(Color.GRAY)

        val ch = binding.chRemember
        ch.isChecked = true

        val rd = binding.rdLarge
        rd.isChecked = true

        // An onLongClickListener for our button
        btnSubmit.setOnLongClickListener {
            val uSurname = binding.editSurname
            val currentSurname = uSurname.text.toString()

            val uFirstname = binding.editFirstname
            val currentFirstname = uFirstname.text.toString()

            val uRemember = binding.chRemember
            val currentRem = uRemember.isChecked.toString()

            val data = "$currentSurname $currentFirstname: $currentRem"
            Toast.makeText(this,data,Toast.LENGTH_LONG).show()
            true
        }

    }
}
