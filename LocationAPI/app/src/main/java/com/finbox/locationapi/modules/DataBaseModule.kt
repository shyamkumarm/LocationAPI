package com.finbox.locationapi.modules

import android.app.Application
import androidx.room.Room
import com.finbox.locationapi.database.DataOperation
import com.finbox.locationapi.database.LocationDataBase
import com.finbox.locationapi.database.LocationDao
import com.finbox.locationapi.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val dbModule = module {
    fun getDatabase(application: Application):LocationDataBase =  Room.databaseBuilder(
        application,
        LocationDataBase::class.java, Constants.DATA_BASE_NAME
    ).build()


    fun locationQuery(database: LocationDataBase): LocationDao {
        return  database.locationDao()
    }

    single { getDatabase(androidApplication()) }
    single { locationQuery(get()) }
    single { DataOperation(get()) }

}

val databaseModule = listOf(dbModule)
