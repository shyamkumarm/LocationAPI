package com.finbox.locationapi.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@Entity
class Location {


    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var timeStamp = Calendar.getInstance().time

    @SuppressLint("SimpleDateFormat")
    @Ignore
    fun convertTime(): String = SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timeStamp)
}