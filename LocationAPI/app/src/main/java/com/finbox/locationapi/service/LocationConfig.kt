package com.finbox.locationapi.service

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.provider.SyncStateContract
import android.widget.Toast
import com.finbox.locationapi.utils.Constants
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class LocationConfig(private val context: Context) {


    fun getClient(): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getLocationCallback() = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
        }
    }

    fun mLocationRequest(): LocationRequest = LocationRequest().apply {
        interval = Constants.UPDATE_INTERVAL_IN_MILLISECONDS
        fastestInterval = Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }

    val settingsBuilder = LocationSettingsRequest.Builder()
        .addLocationRequest(mLocationRequest()).setAlwaysShow(true).build()

    fun enableLocation(act: Activity) {
        LocationServices.getSettingsClient(act).checkLocationSettings(settingsBuilder)
            .addOnCompleteListener { task ->
                try {
                    task.getResult(ApiException::class.java)
                } catch (ex: ApiException) {
                    when (ex.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val resolvableApiException = ex as ResolvableApiException
                            resolvableApiException.startResolutionForResult(
                                act, 3
                            )
                        } catch (e: IntentSender.SendIntentException) {
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        }
                    }
                }
            }
    }
}