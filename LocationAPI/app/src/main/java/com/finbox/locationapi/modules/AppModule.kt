package com.finbox.locationapi.modules


import androidx.work.PeriodicWorkRequest
import com.finbox.locationapi.service.LocationTrackHelper
import com.finbox.locationapi.service.MyWorker
import com.finbox.locationapi.utils.Constants
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private val appModule = module {
    fun getWorkRequest() = PeriodicWorkRequest.Builder(
        MyWorker::class.java, 15,
        TimeUnit.MINUTES
    ).addTag(Constants.WORK_TAG)
        .build()
    single { LocationTrackHelper(getWorkRequest(), get()) }
}


val appModules = listOf(appModule)