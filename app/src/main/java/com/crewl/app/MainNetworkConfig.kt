/**
 * @author Kaan Fırat
 * @version 1.0, 29/01/23
 */

package com.crewl.app

import com.crewl.app.framework.app.NetworkConfig

class MainNetworkConfig: NetworkConfig() {
    override fun baseUrl(): String {
        return ""
    }

    override fun timeOut(): Long {
        return 30L
    }

    override fun isDev(): Boolean {
        return false
    }
}