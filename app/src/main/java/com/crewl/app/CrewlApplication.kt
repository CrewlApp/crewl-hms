package com.crewl.app

import android.content.Context
import com.crewl.app.framework.app.AppInitializer
import com.crewl.app.framework.app.CoreApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CrewlApplication: CoreApplication() {
    @Inject
    lateinit var initializer: AppInitializer

    companion object {
        private var application: CrewlApplication? = null

        fun getContext(): Context? {
            return application?.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        initializer.initialize(application = this@CrewlApplication)
    }
}