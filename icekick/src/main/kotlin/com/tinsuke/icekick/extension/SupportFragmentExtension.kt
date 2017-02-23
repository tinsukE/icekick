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
                                            beforeChange: ((T, T) -> Boolean)?,
                                            afterChange: ((T, T) -> Unit)?) = IceKick.serialState(this, value, beforeChange, afterChange)
fun <T : Parcelable> Fragment.parcelState(instance: Any,
                                          value: T,
                                          beforeChange: ((T, T) -> Boolean)?,
                                          afterChange: ((T, T) -> Unit)?) = IceKick.parcelState(this, value, beforeChange, afterChange)

fun <T> Fragment.nullableState(bundler: Bundler<T>,
                               beforeChange: ((T?, T?) -> Boolean)?,
                               afterChange: ((T?, T?) -> Unit)?) = IceKick.nullableState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Fragment.nullableSerialState(beforeChange: ((T?, T?) -> Boolean)?,
                                                    afterChange: ((T?, T?) -> Unit)?) = IceKick.nullableSerialState(this, beforeChange, afterChange)
fun <T : Parcelable> Fragment.nullableParcelState(beforeChange: ((T?, T?) -> Boolean)?,
                                                  afterChange: ((T?, T?) -> Unit)?) = IceKick.nullableParcelState(this, beforeChange, afterChange)

fun <T> Fragment.lateState(bundler: Bundler<T>,
                           beforeChange: ((T?, T) -> Boolean)?,
                           afterChange: ((T?, T) -> Unit)?) = IceKick.lateState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Fragment.lateSerialState(beforeChange: ((T?, T) -> Boolean)?,
                                                afterChange: ((T?, T) -> Unit)?) = IceKick.lateSerialState(this, beforeChange, afterChange)
fun <T : Parcelable> Fragment.lateParcelState(beforeChange: ((T?, T) -> Boolean)?,
                                              afterChange: ((T?, T) -> Unit)?) = IceKick.lateParcelState(this, beforeChange, afterChange)

fun Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)
