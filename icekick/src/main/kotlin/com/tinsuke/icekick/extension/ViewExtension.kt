package com.tinsuke.icekick.extension

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.tinsuke.icekick.IceKick
import com.tinsuke.icekick.bundler.Bundler
import kotlin.properties.ReadWriteProperty

fun <T : Any> View.state(value: T,
                         beforeChange: ((T, T) -> Boolean)? = null,
                         afterChange: ((T, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.state(this, value, beforeChange, afterChange)
}
fun <T : Any> View.state(value: T,
                         bundler: Bundler,
                         beforeChange: ((T, T) -> Boolean)?,
                         afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
    return IceKick.state(this, value, bundler, beforeChange, afterChange)
}
fun <T> View.nullableState(clazz: Class<T>,
                           beforeChange: ((T?, T?) -> Boolean)? = null,
                           afterChange: ((T?, T?) -> Unit)? = null): ReadWriteProperty<Any, T?> {
    return IceKick.nullableState(this, clazz, beforeChange, afterChange)
}
fun <T> View.nullableState(bundler: Bundler,
                           beforeChange: ((T?, T?) -> Boolean)?,
                           afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
    return IceKick.nullableState(this, bundler, beforeChange, afterChange)
}
fun <T> View.lateState(clazz: Class<T>,
                       beforeChange: ((T?, T) -> Boolean)? = null,
                       afterChange: ((T?, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.lateState(this, clazz, beforeChange, afterChange)
}
fun <T> View.lateState(bundler: Bundler,
                       beforeChange: ((T?, T) -> Boolean)? = null,
                       afterChange: ((T?, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.lateState(this, bundler, beforeChange, afterChange)
}

fun View.freezeInstanceState(parcelable: Parcelable?): Parcelable? {
    if (IceKick.savedInstances[this] == null) {
        return parcelable
    }

    val serializableState = Bundle()
    IceKick.freezeInstanceState(this, serializableState)
    return ViewState(serializableState, parcelable)
}

fun View.unfreezeInstanceState(parcelable: Parcelable): Parcelable? {
    if (IceKick.savedInstances[this] == null || parcelable !is ViewState) {
        return parcelable
    }

    IceKick.unfreezeInstanceState(this, parcelable.state)
    return parcelable.parcelable
}
