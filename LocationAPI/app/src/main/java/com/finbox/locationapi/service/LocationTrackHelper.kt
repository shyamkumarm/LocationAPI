package com.finbox.locationapi.service

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.finbox.locationapi.utils.Constants


class LocationTrackHelper(private val workRequest: PeriodicWorkRequest, context: Context) {


    public val workManager: WorkManager = WorkManager.getInstance(context)


    public fun startWork() =
        workManager.enqueueUniquePeriodicWork(
            "Location",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )


    public fun stopWork() = workManager.cancelAllWorkByTag(Constants.WORK_TAG)


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
}
