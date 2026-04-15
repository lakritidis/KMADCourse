package com.example.kmadcourse

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AnotherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)

        val backBtn = findViewById<Button>(R.id.btn_back)
        backBtn.setOnClickListener {
            finish()
        }

        val b = intent.extras
        val drawMonument = b?.getString("whattodisplay")

        val imgView= findViewById<ImageView>(R.id.img_view)
        when(drawMonument) {
            "whitetower" -> imgView.setImageResource(R.drawable.whitetower)
            "WHITETOWER" -> imgView.setImageResource(R.drawable.whitetower)
            "citadel" -> imgView.setImageResource(R.drawable.acropolis)
            "cat03" -> imgView.setImageResource(R.drawable.prod_cat03)}
    }
}
