package com.example.kmadcourse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ConnectActivity : ComponentActivity() {
    private lateinit var urlBox: EditText
    private lateinit var responseBox: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)
        urlBox = findViewById(R.id.ed_url)
        responseBox = findViewById(R.id.ed_urlcontent)

        val btnFetch: Button = findViewById(R.id.btn_fetch)
        btnFetch.setOnClickListener {
            DownloadTask(this).execute(urlBox.text.toString())
        }

        val btnFetchvolley: Button = findViewById(R.id.btn_fetchvolley)
        btnFetchvolley.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = urlBox.text.toString()

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    responseBox.setText(response)
                },
                { error ->
                    responseBox.setText(error.toString())
                }
            )

            queue.add(stringRequest)
        }

        val btnFetchCoroutine = findViewById<Button>(R.id.btn_fetchcoroutine)
        btnFetchCoroutine.setOnClickListener {
            lifecycleScope.launch {
                val content = downloadUrl(urlBox.text.toString())
                responseBox.setText(content)
            }
        }

    }

    fun updateEditText(text: String) {
        responseBox.setText(text)
    }

    fun displayError(text: String) {
        responseBox.setText(text)
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