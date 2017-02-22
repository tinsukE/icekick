package com.tinsuke.icekick.extension

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tinsuke.icekick.IceKick
import com.tinsuke.icekick.bundler.Bundler
import kotlin.properties.ReadWriteProperty

fun <T : Any> Fragment.state(value: T,
                             beforeChange: ((T, T) -> Boolean)?,
                             afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
    return IceKick.state(this, value, beforeChange, afterChange)
}
fun <T : Any> Fragment.state(value: T,
                             bundler: Bundler,
                             beforeChange: ((T, T) -> Boolean)?,
                             afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
    return IceKick.state(this, value, bundler, beforeChange, afterChange)
}
fun <T> Fragment.nullableState(clazz: Class<T>,
                               beforeChange: ((T?, T?) -> Boolean)?,
                               afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
    return IceKick.nullableState(this, clazz, beforeChange, afterChange)
}
fun <T> Fragment.nullableState(bundler: Bundler,
                               beforeChange: ((T?, T?) -> Boolean)?,
                               afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
    return IceKick.nullableState(this, bundler, beforeChange, afterChange)
}
fun <T> Fragment.lateState(clazz: Class<T>,
                           beforeChange: ((T?, T) -> Boolean)? = null,
                           afterChange: ((T?, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.lateState(this, clazz, beforeChange, afterChange)
}
fun <T> Fragment.lateState(bundler: Bundler,
                           beforeChange: ((T?, T) -> Boolean)? = null,
                           afterChange: ((T?, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.lateState(this, bundler, beforeChange, afterChange)
}

fun Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)
