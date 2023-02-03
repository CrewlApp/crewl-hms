package com.crewl.app.framework.extension

val Any.classTag: String get() = this.javaClass.canonicalName.orEmpty()

val Any.methodTag get() = classTag + object : Any() {}.javaClass.enclosingMethod?.name

fun Any.hashCodeAsString(): String = hashCode().toString()

inline fun <reified T : Any> Any.cast(): T = this as T