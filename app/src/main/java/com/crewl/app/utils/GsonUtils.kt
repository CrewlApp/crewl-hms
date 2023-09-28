package com.crewl.app.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

inline fun <reified T> String.getObject(): T {
    val gson: Gson = GsonBuilder().create()
    return gson.fromJson(this, T::class.java)
}