package com.finbox.locationapi.base

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.finbox.locationapi.R
import com.finbox.locationapi.service.LocationConfig
import com.finbox.locationapi.utils.Constants

abstract class BaseActivity : AppCompatActivity() {
    private val notificationManager by lazy {
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private var notification: NotificationCompat.Builder? = null
    private val flp by lazy { LocationConfig(applicationContext) }

    protected val permissionsList =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

    abstract fun getContentLayout(): View
    abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
        setContentView(getContentLayout())
    }


    protected fun showToast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(baseContext, msg, duration).show()
    }


    protected fun checkLocationPermission(): Boolean {
        val coarse = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val fine = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return coarse == PackageManager.PERMISSION_GRANTED &&
                fine == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                val coarseLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val fineLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (coarseLocation && fineLocation) Toast.makeText(
                    this,
                    getString(R.string.permission_grant),
                    Toast.LENGTH_SHORT
                ).show() else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    protected fun displayNotification() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_ID,
                getString(R.string.tracking),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notification =
            NotificationCompat.Builder(applicationContext, Constants.NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_tracking_icon)
                .setContentTitle(getString(R.string.title_notify))
                //.setProgress(0, 0, true)
                .setOngoing(true)
        notificationManager.notify(Constants.NOTIFICATION_CHANNEL_ID, notification?.build())
    }


    protected fun dismissNotification() {
        notificationManager.cancel(Constants.NOTIFICATION_CHANNEL_ID)
    }

    protected fun enableLocationGPS() {
        flp.enableLocation(this)
    }

}