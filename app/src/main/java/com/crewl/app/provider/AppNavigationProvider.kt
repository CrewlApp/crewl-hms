/**
 * @author Kaan Fırat
 * @version 1.0, 29/01/23
 */

package com.crewl.app.provider

import androidx.navigation.NavController
import com.crewl.app.ui.provider.NavigationProvider

class AppNavigationProvider constructor(private val navController: NavController) : NavigationProvider {
    override fun navigateUp() {
        navController.navigateUp()
    }
}