package com.crewl.app

import com.crewl.app.framework.app.AppInitializer
import com.crewl.app.framework.app.CoreApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CrewlApplication: CoreApplication() {
    @Inject
    lateinit var initializer: AppInitializer

    override fun onCreate() {
        super.onCreate()
        initializer.initialize(application = this@CrewlApplication)
    }
}