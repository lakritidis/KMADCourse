package com.example.kmadcourse

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class BirthdateActivity : AppCompatActivity() {

    private lateinit var picker: DatePickerDialog
    private lateinit var pickerTime: TimePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthdate)

        val res: Resources = resources

        val birthdate_editText = findViewById<EditText>(R.id.birthdate_et)
        val birthdate_button = findViewById<ImageButton>(R.id.birthdate_btn)

        birthdate_button.setOnClickListener {
            val cldr = Calendar.getInstance()
            val current_day = cldr.get(Calendar.DAY_OF_MONTH)
            val current_month = cldr.get(Calendar.MONTH)
            val current_year = cldr.get(Calendar.YEAR)

            picker = DatePickerDialog(

                this@BirthdateActivity, { view, year1, moy, dom ->
                    val mon_str : String
                    if (moy + 1 < 10) {
                        mon_str = "0" + (moy + 1).toString()
                    } else {
                        mon_str = (moy + 1).toString()
                    }

                    val day_str : String
                    if (dom < 10) {
                        day_str = "0" + dom.toString()
                    } else {
                        day_str = dom.toString()
                    }

                    val dateRs = String.format(
                        res.getString(R.string.date_formatted_str),
                        year1.toString(), mon_str, day_str)

                    birthdate_editText.setText(dateRs)
                }, current_year,current_month, current_day
            )
            picker.show()
        }


        val eTextTime: EditText = findViewById(R.id.bd_eText_time)
        val btnGetTime: ImageButton = findViewById(R.id.bd_btn_time)
        btnGetTime.setOnClickListener {
            val cldr = Calendar.getInstance()
            val hour = cldr.get(Calendar.HOUR_OF_DAY)
            val min = cldr.get(Calendar.MINUTE)

            // Time picker dialog
            pickerTime = TimePickerDialog(
                this@BirthdateActivity,
                { view, h, m ->
                    val ampm: String =
                        if (h in 0..11) { "am" } else { "pm" }

                    val timeRs: String =
                        if (m < 10) {
                            String.format(res.getString(R.string.time_formatted_str), h, "0$m", ampm )
                        } else {
                            String.format( res.getString(R.string.time_formatted_str), h, "$m", ampm )
                        }
                    eTextTime.setText(timeRs)
                },
                hour, min, true
            )
            pickerTime.show()
        }
    }
}