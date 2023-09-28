package com.crewl.app

import android.content.Context
import com.crewl.app.framework.app.AppInitializer
import com.crewl.app.framework.app.CoreApplication
import com.crewl.localized_strings_android.CrewlLocalization
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

        application = this@CrewlApplication

        initializer.initialize(application = this@CrewlApplication)

        application?.let {
            CrewlLocalization.init(it.applicationContext, listOf("tr", "en", "czh"))
        }
    }
}