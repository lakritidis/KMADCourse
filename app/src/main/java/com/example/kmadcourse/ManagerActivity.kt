package com.example.kmadcourse

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.example.kmadcourse.databinding.ActivityManagerBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    private lateinit var androidId: String
    private var uploadJob: Job? = null

    companion object {
        private const val UPDATE_INTERVAL = 10_000L
        private const val LOCATION_INTERVAL = 5_000L
        private const val FASTEST_INTERVAL = 2_000L
        private const val CHANNEL_ID = "location_channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupLocationCallback()

        binding.btnStartTask.setOnClickListener { startRepeatingTask() }
        binding.btnStopTask.setOnClickListener { stopRepeatingTask() }

        checkLocationPermission()
    }

    // -------------------------------------------------
    // Permission Handling
    // -------------------------------------------------

    private val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                startLocationUpdates()
            } else {
                showLocationUnavailable()
            }
        }

    private fun checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // -------------------------------------------------
    // Location
    // -------------------------------------------------

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                currentLocation = result.lastLocation
                updateLocationUI()
            }
        }
    }

    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_INTERVAL
        )
            .setMinUpdateIntervalMillis(FASTEST_INTERVAL)
            .build()

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // -------------------------------------------------
    // Repeating Upload Task
    // -------------------------------------------------

    private fun startRepeatingTask() {
        if (uploadJob != null) return

        uploadJob = lifecycleScope.launch {
            while (isActive) {
                sendLocationToServer()
                delay(UPDATE_INTERVAL)
            }
        }
    }

    private fun stopRepeatingTask() {
        uploadJob?.cancel()
        uploadJob = null
    }

    // -------------------------------------------------
    // Networking
    // -------------------------------------------------

    private suspend fun sendLocationToServer() {

        val location = currentLocation ?: return

        val latitude = location.latitude
        val longitude = location.longitude
        val altitude = location.altitude
        val speed = location.speed
        val bearing = location.bearing

        val url =
            "http://mad.mywork.gr/userlocation.php?" + "uid=$androidId" +
                    "&lat=$latitude" + "&lon=$longitude" + "&alt=$altitude" + "&spd=$speed" +
                    "&brn=$bearing"

        try {
            val responseBody = withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response -> response.body()?.string() }
            }

            binding.edResponse.setText(responseBody)
            displayNotification("ok", "DATA SENT")
        } catch (e: Exception) {
            binding.edResponse.setText(e.toString())
        }
    }

    // -------------------------------------------------
    // UI
    // -------------------------------------------------

    private fun updateLocationUI() {
        val location = currentLocation ?: return
        binding.tvCoord.text = getString(
            R.string.coord_key,
            location.latitude,
            location.longitude
        )

        binding.tvAltit.text = getString(
            R.string.double_key,
            location.altitude
        )

        binding.tvSpeed.text = getString(
            R.string.double_key,
            location.speed
        )

        binding.tvBrng.text = getString(
            R.string.double_key,
            location.bearing
        )
    }

    private fun showLocationUnavailable() {
        binding.tvCoord.setText(R.string.na)
        binding.tvAltit.setText(R.string.na)
        binding.tvSpeed.setText(R.string.na)
        binding.tvBrng.setText(R.string.na)
    }

    // -------------------------------------------------
    // Notification
    // -------------------------------------------------

    private fun displayNotification(
        title: String,
        message: String
    ) {

        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        )

        val pendingIntent = PendingIntent.getActivity(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.plus)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(
                    RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_NOTIFICATION
                    )
                )
                .setContentIntent(pendingIntent)

        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "Location Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            manager.createNotificationChannel(channel)
        }

        manager.notify(1, builder.build())
    }

    // -------------------------------------------------
    // Lifecycle
    // -------------------------------------------------

    override fun onDestroy() {
        super.onDestroy()

        stopRepeatingTask()
        stopLocationUpdates()
    }
}
