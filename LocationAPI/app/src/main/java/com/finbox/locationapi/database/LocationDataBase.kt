package com.finbox.locationapi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finbox.locationapi.model.Location
import com.finbox.locationapi.service.Converters


@Database(entities = [Location::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocationDataBase() : RoomDatabase() {
    abstract fun locationDao(): LocationDao

}

