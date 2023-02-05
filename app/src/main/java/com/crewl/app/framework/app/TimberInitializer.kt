package com.crewl.app.framework.app

import com.crewl.app.framework.platform.isHMS
import timber.log.Timber

class TimberInitializer(private val isDev: Boolean): AppInitializer {
    override fun initialize(application: CoreApplication) {
            Timber.plant(Timber.DebugTree())
    }
}