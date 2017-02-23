package com.tinsuke.icekick.extension

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import com.tinsuke.icekick.IceKick
import com.tinsuke.icekick.bundler.Bundler
import java.io.Serializable

fun <T> Fragment.state(value: T,
                       bundler: Bundler<T>,
                       beforeChange: ((T, T) -> Boolean)? = null,
                       afterChange: ((T, T) -> Unit)? = null) = IceKick.state(this, value, bundler, beforeChange, afterChange)
fun <T : Serializable> Fragment.serialState(value: T,
                                            beforeChange: ((T, T) -> Boolean)? = null,
                                            afterChange: ((T, T) -> Unit)? = null) = IceKick.serialState(this, value, beforeChange, afterChange)
fun <T : Parcelable> Fragment.parcelState(value: T,
                                          beforeChange: ((T, T) -> Boolean)? = null,
                                          afterChange: ((T, T) -> Unit)? = null) = IceKick.parcelState(this, value, beforeChange, afterChange)

fun <T> Fragment.nullableState(bundler: Bundler<T>,
                               beforeChange: ((T?, T?) -> Boolean)? = null,
                               afterChange: ((T?, T?) -> Unit)? = null) = IceKick.nullableState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Fragment.serialNullableState(beforeChange: ((T?, T?) -> Boolean)? = null,
                                                    afterChange: ((T?, T?) -> Unit)? = null) = IceKick.serialNullableState(this, beforeChange, afterChange)
fun <T : Parcelable> Fragment.parcelNullableState(beforeChange: ((T?, T?) -> Boolean)? = null,
                                                  afterChange: ((T?, T?) -> Unit)? = null) = IceKick.parcelNullableState(this, beforeChange, afterChange)

fun <T> Fragment.lateState(bundler: Bundler<T>,
                           beforeChange: ((T?, T) -> Boolean)? = null,
                           afterChange: ((T?, T) -> Unit)? = null) = IceKick.lateState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Fragment.serialLateState(beforeChange: ((T?, T) -> Boolean)? = null,
                                                afterChange: ((T?, T) -> Unit)? = null) = IceKick.serialLateState(this, beforeChange, afterChange)
fun <T : Parcelable> Fragment.parcelLateState(beforeChange: ((T?, T) -> Boolean)? = null,
                                              afterChange: ((T?, T) -> Unit)? = null) = IceKick.parcelLateState(this, beforeChange, afterChange)

fun Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)
