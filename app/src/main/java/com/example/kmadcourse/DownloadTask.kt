package com.example.kmadcourse

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DownloadTask(private val activity: ConnectActivity) :
    AsyncTask<String, Void, String>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): String {
        val urlString = params[0] ?: return ""
        val result = StringBuilder()

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                result.append(line).append("\n")
            }
            reader.close()
        } catch (e: Exception) {
            activity.displayError(e.message.toString())
        }
        return result.toString()
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: String?) {
            activity.updateEditText(result.toString())
    }
}