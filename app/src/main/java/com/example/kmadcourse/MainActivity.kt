package com.example.kmadcourse

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.kmadcourse.databinding.ActivityMainBinding
import androidx.core.graphics.toColorInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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
/*
        val another_ref_to_surname_box =
            findViewById<EditText>(R.id.edit_surname)
        another_ref_to_surname_box.setText("Changed again!")
        another_ref_to_surname_box.setTextSize(20.0F)
        another_ref_to_surname_box.setTextColor("#bb2539".toColorInt())
        another_ref_to_surname_box.setBackgroundColor(Color.GRAY)
*/
        val checkbox_ref = act_binding.chRem
        checkbox_ref?.setChecked(false)

        val btn_ok = findViewById<Button>(R.id.btn_ok)
        btn_ok.setOnClickListener {
            val edittext_ref = findViewById<EditText>(R.id.edit_surname)
            val user_surname = edittext_ref.getText().toString()

            val firstname_ref = findViewById<EditText>(R.id.edit_firstname)
            val user_firstname = firstname_ref.getText().toString()

            val checkbox_ref = findViewById<CheckBox>(R.id.ch_rem)
            val user_selection = checkbox_ref.isChecked().toString()

            Toast.makeText(this,
                "USER DATA: " + user_surname + ", " + user_firstname + ", " + user_selection,
                Toast.LENGTH_LONG).show()
        }

        btn_ok.setOnLongClickListener {
            Toast.makeText(this,
                "I WAS LONG PRESSED",
                Toast.LENGTH_LONG).show()

            true
        }

        val spinner_ref = findViewById<Spinner>(R.id.title_selector)

        // Spinner options
        val spinnerOptions = arrayOf(" ", "Mr", "Mrs", "Miss", "Dr", "Professor", "General")

        // Create adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerOptions).
            apply{
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        spinner_ref.adapter = adapter

        // Set the default selected item
        spinner_ref.setSelection(4)

        val imagebutton_ref = findViewById<ImageButton>(R.id.btn_gotoanother)
        imagebutton_ref.setOnClickListener {
            val edittext_ref = findViewById<EditText>(R.id.edit_surname)
            val myIntent = Intent(this, AnotherActivity::class.java)

            val b = Bundle().apply {
                putString("whattodisplay", edittext_ref.getText().toString())
            }
            myIntent.putExtras(b)
            startActivity(myIntent)
        }
    }

    private suspend fun downloadUrl(urlString: String): String {
        return withContext(Dispatchers.IO) {
            val result = StringBuilder()
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val reader = BufferedReader(
                    InputStreamReader(connection.inputStream)
                )

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    result.append(line).append("\n")
                }
                reader.close()
            } catch (e: Exception) {
                return@withContext e.message ?: "Error"
            }
            result.toString()
        }
    }
}

