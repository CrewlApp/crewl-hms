/**
 * @author Kaan Fırat
 * @version 1.0, 29/01/23
 */

package com.crewl.app.framework.app

abstract class NetworkConfig {
    abstract fun baseUrl(): String

    abstract fun timeOut(): Long

    open fun isDev() = true
}