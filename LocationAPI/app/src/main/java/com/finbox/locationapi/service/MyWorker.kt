package com.finbox.locationapi.service

import android.content.Context
import android.location.Location
import android.net.ParseException
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.finbox.locationapi.database.DataOperation
import com.finbox.locationapi.utils.Constants
import com.google.android.gms.tasks.Task
import org.koin.core.KoinComponent
import org.koin.core.inject

class MyWorker(private val mContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(mContext, workerParams),
    KoinComponent {
    private var flpConfig: LocationConfig? = null
    private val appRepository: DataOperation by inject()

    override suspend fun doWork(): Result {
        Log.d(Constants.WORK_TAG, "doWork: Done")
        try {
            flpConfig = LocationConfig(mContext).apply {
                getClient()
                    .lastLocation
                    ?.addOnCompleteListener { task: Task<Location?> ->
                        if (task.isSuccessful && task.result != null) {
                            Log.d(
                                Constants.WORK_TAG,
                                "Location : ${task.result?.latitude},${task.result?.longitude}"
                            )
                            appRepository.insertLocation(task.result)
                            getClient().removeLocationUpdates(getLocationCallback())
                        } else {
                            Log.w(Constants.WORK_TAG, "Failed to get location.")
                        }
                    }
            }
        } catch (unlikely: SecurityException) {
            Log.e(Constants.WORK_TAG, "Lost location permission.$unlikely")
        }
        try {
            flpConfig?.getClient()?.requestLocationUpdates(flpConfig?.mLocationRequest(), null)
        } catch (unlikely: SecurityException) {
            Log.e(
                Constants.WORK_TAG,
                "Lost location permission. Could not request updates. $unlikely"
            )
        } catch (ignored: ParseException) {
        }
        return Result.success()
    }


}