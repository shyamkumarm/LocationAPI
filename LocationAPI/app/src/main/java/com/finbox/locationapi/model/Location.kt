package com.finbox.locationapi.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity
class Location {


    companion object {
        fun newInstance(): Location {
            return Location()
        }
    }

    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var timeStamp = Calendar.getInstance().time


}