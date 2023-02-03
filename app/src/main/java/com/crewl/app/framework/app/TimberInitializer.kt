package com.crewl.app.framework.app

import com.crewl.app.framework.platform.isHMS
import timber.log.Timber

class TimberInitializer(private val isDev: Boolean): AppInitializer {
    override fun initialize(application: CoreApplication) {
        if (isDev)
            Timber.plant(Timber.DebugTree())
        else {
            if (application.applicationContext.isHMS())
                Timber.plant(FirebaseCrashlyticsReportTree())
            else
                Timber.plant(FirebaseCrashlyticsReportTree())
        }
    }
}