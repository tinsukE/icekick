package com.tinsuke.icekick

import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SavedProperty<in R, T : Serializable>(var value: Serializable, val onSet: ((T) -> Unit)? = null) : ReadWriteProperty<R, T> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        return value as T
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value = value
        onSet?.invoke(value)
    }
}