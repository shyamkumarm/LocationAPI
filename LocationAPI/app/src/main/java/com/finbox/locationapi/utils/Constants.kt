package com.finbox.locationapi.utils

import androidx.work.WorkManager

object Constants{


   const val DATA_BASE_NAME = "LocationDb"
   const val WORK_TAG = "LocationTracker"
   //req code
   const val PERMISSION_REQUEST_CODE = 200

   /**
    * The desired interval for location updates. Inexact. Updates may be more or less frequent.
    */
    const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

   /**
    * The fastest rate for active location updates. Updates will never be more frequent
    * than this value.
    */
    const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
      UPDATE_INTERVAL_IN_MILLISECONDS / 2

    const val NOTIFICATION_ID: String = "notificationId"
    const val NOTIFICATION_CHANNEL_ID = 2
    const val LOCATION_LAT = "LAT"
    const val LOCATION_LONG = "LONG"
    const val WORK_MANAGER_UNIQUE_NAME = "Location"
}