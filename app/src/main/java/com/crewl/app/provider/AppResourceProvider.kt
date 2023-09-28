/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.provider

import com.crewl.app.ui.provider.ResourceProvider
import com.crewl.localized_strings_android.CrewlLocalization

class AppResourceProvider : ResourceProvider {
    override fun getString(id: Int): String {
        return CrewlLocalization.getLocalizedString(id = id)
    }
}