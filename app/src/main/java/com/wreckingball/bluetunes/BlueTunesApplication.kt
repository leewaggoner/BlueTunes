package com.wreckingball.bluetunes

import android.app.Application
import com.wreckingball.bluetunes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlueTunesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BlueTunesApplication)
            modules(appModule)
        }
    }
}