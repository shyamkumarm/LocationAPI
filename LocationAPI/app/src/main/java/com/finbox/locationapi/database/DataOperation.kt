package com.finbox.locationapi.database

import androidx.lifecycle.MutableLiveData
import com.finbox.locationapi.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class DataOperation(private val dao: LocationDao) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun getUIData(
        viewModelList: MutableLiveData<List<Location>>
    ) {
        launch {
            val res = async { dao.getLocationList() }
            viewModelList.postValue(res.await())
        }

    }

    public fun insertLocation(location: android.location.Location?) = launch {
        dao.insertLocation(
            Location().apply {
                latitude = location?.latitude ?: latitude
                longitude = location?.latitude ?: longitude
                timeStamp = Calendar.getInstance().time
            })

    }
}
