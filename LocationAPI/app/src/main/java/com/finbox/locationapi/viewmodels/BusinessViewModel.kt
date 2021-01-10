package com.finbox.locationapi.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.finbox.locationapi.database.DataOperation
import com.finbox.locationapi.database.LocationDao
import com.finbox.locationapi.model.Location
import com.finbox.locationapi.service.LocationTrackHelper


class BusinessViewModel(private val dataBase: DataOperation,private val worker:LocationTrackHelper) : ViewModel() {

    var locationUIData = MutableLiveData<List<Location>>()

    fun getLocationList() {
        dataBase.getUIData(locationUIData)
    }

    fun startWork() = worker.startWork()
    fun stopWork() = worker.stopWork()
    fun isWorkRunning() = worker.isWorkScheduled()


}