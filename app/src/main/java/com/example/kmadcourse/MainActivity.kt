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

        val btn = binding.myfirstButton
        val btn2 = binding.mysecondButton
        btn?.setText("I was changed!")
    }
}
