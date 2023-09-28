package com.crewl.app.framework.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

val Context.currentActivity get(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.currentActivity
    else -> null
}