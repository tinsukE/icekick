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
                                            beforeChange: ((T, T) -> Boolean)?,
                                            afterChange: ((T, T) -> Unit)?) = IceKick.serialState(this, value, beforeChange, afterChange)
fun <T : Parcelable> Activity.parcelState(instance: Any,
                                          value: T,
                                          beforeChange: ((T, T) -> Boolean)?,
                                          afterChange: ((T, T) -> Unit)?) = IceKick.parcelState(this, value, beforeChange, afterChange)

fun <T> Activity.nullableState(bundler: Bundler<T>,
                               beforeChange: ((T?, T?) -> Boolean)?,
                               afterChange: ((T?, T?) -> Unit)?) = IceKick.nullableState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Activity.nullableSerialState(beforeChange: ((T?, T?) -> Boolean)?,
                                                    afterChange: ((T?, T?) -> Unit)?) = IceKick.nullableSerialState(this, beforeChange, afterChange)
fun <T : Parcelable> Activity.nullableParcelState(beforeChange: ((T?, T?) -> Boolean)?,
                                                  afterChange: ((T?, T?) -> Unit)?) = IceKick.nullableParcelState(this, beforeChange, afterChange)

fun <T> Activity.lateState(bundler: Bundler<T>,
                           beforeChange: ((T?, T) -> Boolean)?,
                           afterChange: ((T?, T) -> Unit)?) = IceKick.lateState(this, bundler, beforeChange, afterChange)
fun <T : Serializable> Activity.lateSerialState(beforeChange: ((T?, T) -> Boolean)?,
                                                afterChange: ((T?, T) -> Unit)?) = IceKick.lateSerialState(this, beforeChange, afterChange)
fun <T : Parcelable> Activity.lateParcelState(beforeChange: ((T?, T) -> Boolean)?,
                                              afterChange: ((T?, T) -> Unit)?) = IceKick.lateParcelState(this, beforeChange, afterChange)

fun Activity.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Activity.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)
