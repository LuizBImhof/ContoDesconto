package com.unidavi.tc.conto

import android.app.Application
import timber.log.Timber

class ContoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        populateDatabase(this)
    }
}