/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.provider

import android.content.Context
import com.crewl.app.ui.provider.ResourceProvider

class AppResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}