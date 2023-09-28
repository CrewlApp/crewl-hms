package com.crewl.app.data.source.local

import android.content.Context
import com.crewl.app.data.model.country.Country
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class CountryDataSource(private val context: Context) {
    @OptIn(ExperimentalSerializationApi::class)
    fun fetch(): List<Country> {
        val inputStream = context.assets.open(FILE_NAME)
        return Json.decodeFromStream(inputStream)
    }

    companion object {
        private const val FILE_NAME = "Countries.json"
    }
}
