package com.tinsuke.icekick

import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by rafalciurkot on 19.08.2016
 */
class LateinitSavedProperty<in R, T : Serializable> : ReadWriteProperty<R, T> {
    var value: Serializable? = null

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == null)
            throw UninitializedPropertyAccessException("LateinitSavedProperty was accessed before it was set")

        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value = value
    }
}