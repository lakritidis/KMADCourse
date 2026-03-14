package com.example.kmadcourse

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.kmadcourse.databinding.ActivityMainBinding
import androidx.core.graphics.toColorInt

class MainActivity : ComponentActivity() {
    private lateinit var act_binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act_binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(act_binding.root)

        val lastname_edit = act_binding.editSurname
        val firstname_edit = act_binding.editFirstname

        lastname_edit.setText(R.string.text_message1)
        firstname_edit.setText("Another piece of text")
        firstname_edit.setTextSize(24.0F)
        firstname_edit.setTextColor("#0000ff".toColorInt())
        firstname_edit.setBackgroundColor("#efefef".toColorInt())

        val another_ref_to_surname_box =
            findViewById<EditText>(R.id.edit_surname)
        another_ref_to_surname_box.setText("Changed again!")
        another_ref_to_surname_box.setTextSize(20.0F)
        another_ref_to_surname_box.setTextColor("#bb2539".toColorInt())
        another_ref_to_surname_box.setBackgroundColor(Color.GRAY)

        val checkbox_ref = act_binding.chRem
        checkbox_ref?.setChecked(false)

        val btn_ok = findViewById<Button>(R.id.btn_ok)
        btn_ok.setOnClickListener {
            val user_surname = lastname_edit.getText().toString()
            // val display_message =  "I was pressed!" + user_surname
            val is_cb_sel = checkbox_ref?.isChecked
            if (is_cb_sel == true) {
                Toast.makeText(this, "ANOTHER MESSAGE", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun compute_distance() {

    }
}

