/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.framework.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}