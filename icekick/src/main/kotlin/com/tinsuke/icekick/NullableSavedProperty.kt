package com.tinsuke.icekick

import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class NullableSavedProperty<in R, T : Serializable>(val onSet: ((T?) -> Unit)? = null) : ReadWriteProperty<R, T?> {
    var value: Serializable? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: R, property: KProperty<*>): T? {
        return value as T?
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        this.value = value
        onSet?.invoke(value)
    }
}