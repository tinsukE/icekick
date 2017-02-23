package com.tinsuke.icekick.extension

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.tinsuke.icekick.IceKick
import com.tinsuke.icekick.bundler.Bundler
import java.io.Serializable

fun <T> View.state(value: T,
                   bundler: Bundler<T>,
                   beforeChange: ((T, T) -> Boolean)? = null,
                   afterChange: ((T, T) -> Unit)? = null) = IceKick.state(this, value, bundler, beforeChange, afterChange)
fun <T : Serializable> View.serialState(value: T,
                                        beforeChange: ((T, T) -> Boolean)? = null,
                                        afterChange: ((T, T) -> Unit)? = null) = IceKick.serialState(this, value, beforeChange, afterChange)
fun <T : Parcelable> View.parcelState(value: T,
                                      beforeChange: ((T, T) -> Boolean)? = null,
                                      afterChange: ((T, T) -> Unit)? = null) = IceKick.parcelState(this, value, beforeChange, afterChange)

fun <T> View.nullableState(bundler: Bundler<T>,
                           beforeChange: ((T?, T?) -> Boolean)? = null,
                           afterChange: ((T?, T?) -> Unit)? = null) = IceKick.nullableState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> View.serialNullableState(beforeChange: ((T?, T?) -> Boolean)? = null,
                                                afterChange: ((T?, T?) -> Unit)? = null) = IceKick.serialNullableState(this, beforeChange, afterChange)
fun <T : Parcelable> View.parcelNullableState(beforeChange: ((T?, T?) -> Boolean)? = null,
                                              afterChange: ((T?, T?) -> Unit)? = null) = IceKick.parcelNullableState(this, beforeChange, afterChange)

fun <T> View.lateState(bundler: Bundler<T>,
                       beforeChange: ((T?, T) -> Boolean)? = null,
                       afterChange: ((T?, T) -> Unit)? = null) = IceKick.lateState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> View.serialLateState(beforeChange: ((T?, T) -> Boolean)? = null,
                                            afterChange: ((T?, T) -> Unit)? = null) = IceKick.serialLateState(this, beforeChange, afterChange)
fun <T : Parcelable> View.parcelLateState(beforeChange: ((T?, T) -> Boolean)? = null,
                                          afterChange: ((T?, T) -> Unit)? = null) = IceKick.parcelLateState(this, beforeChange, afterChange)

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
