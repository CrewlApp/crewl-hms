package com.crewl.app.helper.countryCode

import android.content.Context
import android.telephony.TelephonyManager
import com.crewl.app.CrewlApplication.Companion.getContext
import com.crewl.app.data.model.country.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class CountryCodeHelper {
    companion object {
        @JvmStatic
        fun getCountries(): MutableList<Country> {
            val jsonFileString = getJsonDataFromAsset(getContext()!!, "Countries.json")
            val type = object : TypeToken<List<Country>>() {}.type

            return Gson().fromJson(jsonFileString, type)
        }
        @JvmStatic
        fun getEmojiFromLocal(countryCode: String): String {
            val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6

            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }
        @JvmStatic
        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }

            return jsonString
        }
        @JvmStatic
        fun getCountryFromLocal(): Country? {
            getContext()?.let { context: Context ->
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val userCountryCode = telephonyManager.networkCountryIso

                val countries = getCountries()

                countries.forEach { country ->
                    if (country.code.lowercase().contains(userCountryCode.lowercase()))
                        return country
                }
            }

            return null
        }
        @JvmStatic
        fun getCountry(countryCode: String): Country? {
            val countries = getCountries()
            countries.forEach { country ->
                if (country.code.lowercase().contains(countryCode.lowercase()))
                    return country
            }

            return null
        }
        @JvmStatic
        fun List<Country>.searchCountries(query: String): MutableList<Country> {
            val countryList = mutableListOf<Country>()
            this.forEach { country ->
                if (country.name.lowercase().contains(query.lowercase()) || country.dialogCode.contains(query.lowercase()))
                    countryList.add(country)
            }

            return countryList
        }
        @JvmStatic
        fun formatCountry(country: Country): String {
            val emoji = getEmojiFromLocal(countryCode = country.code)

            return "$emoji ${country.dialogCode}"
        }

        @JvmStatic
        fun getUserLocal(): String? {
            val telephonyManager = getContext()?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.networkCountryIso
        }
    }
}

