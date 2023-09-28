/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.provider

import androidx.navigation.NavController
import com.crewl.app.ui.provider.NavigationProvider

class AppNavigationProvider constructor(private val navController: NavController) : NavigationProvider {
    override fun navigateUp() {
        navController.navigateUp()
    }
}