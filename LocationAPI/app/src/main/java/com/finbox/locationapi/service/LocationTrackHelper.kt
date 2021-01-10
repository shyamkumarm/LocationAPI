package com.finbox.locationapi.service

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.finbox.locationapi.utils.Constants
import com.google.common.util.concurrent.ListenableFuture


class LocationTrackHelper(private val workRequest: PeriodicWorkRequest, context: Context) {


    private val workManager: WorkManager = WorkManager.getInstance(context)


    fun startWork() =
        workManager.enqueueUniquePeriodicWork(
            Constants.WORK_MANAGER_UNIQUE_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )


    fun stopWork() = workManager.cancelAllWorkByTag(Constants.WORK_TAG)


    public fun isWorkScheduled(): Boolean {
        var running = false
        workManager.getWorkInfosByTag(Constants.WORK_TAG).get().run {
            if (this.isEmpty()) return false
            for (workStatus in this) {
                running =
                    workStatus.state == WorkInfo.State.RUNNING || workStatus.state == WorkInfo.State.ENQUEUED
            }
            return running
        }

    }

    fun getResult(): LiveData<WorkInfo> {
       return workManager.getWorkInfoByIdLiveData(workRequest.id)
    }
}
