package com.finbox.locationapi.database

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.finbox.locationapi.model.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocation(language : Location)

    @Query("SELECT * FROM Location  order by timeStamp desc")
    fun getLocationList(): List<Location>
}
