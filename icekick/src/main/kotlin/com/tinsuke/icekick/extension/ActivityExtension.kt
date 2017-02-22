package com.tinsuke.icekick.extension

import android.app.Activity
import android.os.Bundle
import com.tinsuke.icekick.IceKick
import com.tinsuke.icekick.bundler.Bundler
import kotlin.properties.ReadWriteProperty

fun <T : Any> Activity.state(value: T,
                             beforeChange: ((T, T) -> Boolean)?,
                             afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
    return IceKick.state(this, value, beforeChange, afterChange)
}
fun <T : Any> Activity.state(value: T,
                             bundler: Bundler,
                             beforeChange: ((T, T) -> Boolean)?,
                             afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
    return IceKick.state(this, value, bundler, beforeChange, afterChange)
}
fun <T> Activity.nullableState(clazz: Class<T>,
                               beforeChange: ((T?, T?) -> Boolean)?,
                               afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
    return IceKick.nullableState(this, clazz, beforeChange, afterChange)
}
fun <T> Activity.nullableState(bundler: Bundler,
                               beforeChange: ((T?, T?) -> Boolean)?,
                               afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
    return IceKick.nullableState(this, bundler, beforeChange, afterChange)
}
fun <T> Activity.lateState(clazz: Class<T>,
                           beforeChange: ((T?, T) -> Boolean)? = null,
                           afterChange: ((T?, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.lateState(this, clazz, beforeChange, afterChange)
}
fun <T> Activity.lateState(bundler: Bundler,
                           beforeChange: ((T?, T) -> Boolean)? = null,
                           afterChange: ((T?, T) -> Unit)? = null): ReadWriteProperty<Any, T> {
    return IceKick.lateState(this, bundler, beforeChange, afterChange)
}

fun Activity.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Activity.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)
