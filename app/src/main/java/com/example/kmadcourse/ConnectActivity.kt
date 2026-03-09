package com.example.kmadcourse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope

import com.example.kmadcourse.databinding.ActivityConnectBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ConnectActivity : ComponentActivity() {
    private lateinit var binding: ActivityConnectBinding
    private lateinit var url_box: EditText
    private lateinit var response_box: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)
        url_box = findViewById(R.id.ed_url)
        response_box = findViewById(R.id.ed_urlcontent)

        var btn_fetch: Button = findViewById(R.id.btn_fetch)
        btn_fetch.setOnClickListener {
            DownloadTask(this).execute(url_box.text.toString())
        }
/*
        var btn_fetchvolley: Button = findViewById(R.id.btn_fetchvolley)
        btn_fetchvolley.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = url_box.text.toString()

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    response_box.setText(response)
                },
                { error ->
                    response_box.setText("That didn't work!")
                }
            )

            queue.add(stringRequest)

        }
*/
        var btn_fetch_coroutine: Button = findViewById(R.id.btn_fetchcoroutine)
        btn_fetch_coroutine.setOnClickListener {
            lifecycleScope.launch {
                val content = downloadUrl(url_box.text.toString())
                response_box.setText(content)
            }
        }

    }

    fun updateEditText(text: String) {
        response_box.setText(text)
    }

    fun displayError(text: String) {
        response_box.setText(text)
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