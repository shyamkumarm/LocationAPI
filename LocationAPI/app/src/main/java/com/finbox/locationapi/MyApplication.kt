package com.finbox.locationapi

import android.app.Application
import com.finbox.locationapi.modules.appModules
import com.finbox.locationapi.modules.databaseModule
import com.finbox.locationapi.modules.viewModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(databaseModule + viewModules + appModules)
        }
    }
}