package com.finbox.locationapi.service

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.finbox.locationapi.R
import com.finbox.locationapi.database.DataOperation
import com.finbox.locationapi.utils.Constants
import com.finbox.locationapi.utils.Constants.LOCATION_LAT
import com.finbox.locationapi.utils.Constants.LOCATION_LONG
import com.google.android.gms.tasks.Task
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MyWorker(private val mContext: Context, workerParams: WorkerParameters) :
    ListenableWorker(mContext, workerParams),
    KoinComponent {

    private var flpConfig: LocationConfig? = null
    private val appRepository: DataOperation by inject()

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->
            doWorkOnListenableCallback(completer)

        }
    }


    private fun doWorkOnListenableCallback(completer: CallbackToFutureAdapter.Completer<Result>) {
        // if (flpConfig?.isPermissionGranted() == true) {
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
                            CoroutineScope(Dispatchers.Main).launch {
                                completer.set(Result.success(Data.EMPTY))
                            }
                            getClient().removeLocationUpdates(getLocationCallback())
                        } else {
                            Log.w(Constants.WORK_TAG, mContext.getString(R.string.failed))
                            completer.set(Result.failure(Data.EMPTY))
                        }
                    }?.addOnFailureListener { exception ->
                        Log.e(
                            Constants.WORK_TAG,
                            "Something went wrong getting the location -> $exception"
                        )
                        completer.set(Result.failure(Data.EMPTY))
                    }
            }
            flpConfig?.getClient()?.requestLocationUpdates(flpConfig?.mLocationRequest(), null)
        } catch (unlikely: SecurityException) {
            Log.e(Constants.WORK_TAG, "Lost location permission.$unlikely")
        }

    }

    private fun sendResult(location: Location?): Data {
        return workDataOf(LOCATION_LAT to location?.latitude,LOCATION_LONG to location?.longitude  )

    }


}