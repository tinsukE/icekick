package com.tinsuke.icekick.extension

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import com.tinsuke.icekick.IceKick
import com.tinsuke.icekick.bundler.Bundler
import java.io.Serializable

fun <T> Activity.state(value: T,
                       bundler: Bundler<T>,
                       beforeChange: ((T, T) -> Boolean)? = null,
                       afterChange: ((T, T) -> Unit)? = null) = IceKick.state(this, value, bundler, beforeChange, afterChange)
fun <T : Serializable> Activity.serialState(value: T,
                                            beforeChange: ((T, T) -> Boolean)? = null,
                                            afterChange: ((T, T) -> Unit)? = null) = IceKick.serialState(this, value, beforeChange, afterChange)
fun <T : Parcelable> Activity.parcelState(value: T,
                                          beforeChange: ((T, T) -> Boolean)? = null,
                                          afterChange: ((T, T) -> Unit)? = null) = IceKick.parcelState(this, value, beforeChange, afterChange)

fun <T> Activity.nullableState(bundler: Bundler<T>,
                               beforeChange: ((T?, T?) -> Boolean)? = null,
                               afterChange: ((T?, T?) -> Unit)? = null) = IceKick.nullableState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Activity.serialNullableState(beforeChange: ((T?, T?) -> Boolean)? = null,
                                                    afterChange: ((T?, T?) -> Unit)? = null) = IceKick.serialNullableState(this, beforeChange, afterChange)
fun <T : Parcelable> Activity.parcelNullableState(beforeChange: ((T?, T?) -> Boolean)? = null,
                                                  afterChange: ((T?, T?) -> Unit)? = null) = IceKick.parcelNullableState(this, beforeChange, afterChange)

fun <T> Activity.lateState(bundler: Bundler<T>,
                           beforeChange: ((T?, T) -> Boolean)? = null,
                           afterChange: ((T?, T) -> Unit)? = null) = IceKick.lateState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Activity.serialLateState(beforeChange: ((T?, T) -> Boolean)? = null,
                                                afterChange: ((T?, T) -> Unit)? = null) = IceKick.serialLateState(this, beforeChange, afterChange)
fun <T : Parcelable> Activity.parcelLateState(beforeChange: ((T?, T) -> Boolean)? = null,
                                              afterChange: ((T?, T) -> Unit)? = null) = IceKick.parcelLateState(this, beforeChange, afterChange)

fun Activity.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Activity.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)
