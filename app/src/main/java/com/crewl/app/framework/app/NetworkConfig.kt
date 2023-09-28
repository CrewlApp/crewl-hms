/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.framework.app

abstract class NetworkConfig {
    abstract fun baseUrl(): String

    abstract fun timeOut(): Long

    open fun isDev() = true
}