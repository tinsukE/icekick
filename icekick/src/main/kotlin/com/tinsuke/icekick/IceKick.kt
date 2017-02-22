package com.tinsuke.icekick

import android.os.Bundle
import android.os.Parcelable
import com.tinsuke.icekick.bundler.Bundler
import com.tinsuke.icekick.bundler.ParcelableBundler
import com.tinsuke.icekick.bundler.SerializableBundler
import com.tinsuke.icekick.property.BaseSavedProperty
import com.tinsuke.icekick.property.LateSavedProperty
import com.tinsuke.icekick.property.NullableSavedProperty
import com.tinsuke.icekick.property.SavedProperty
import java.io.Serializable
import java.security.InvalidParameterException
import java.util.*
import kotlin.properties.ReadWriteProperty

internal object IceKick {

    val savedInstances = WeakHashMap<Any, MutableList<BaseSavedProperty<*>>>()

    private val serializableBundler by lazy { SerializableBundler() }
    private val parcelableBundler by lazy { ParcelableBundler() }

    private fun getOrPutSavedProperties(instance: Any) = savedInstances.getOrPut(instance) { mutableListOf() }

    private fun bundlerForClass(clazz: Class<*>): Bundler {
        if (Serializable::class.java.isAssignableFrom(clazz)) {
            return serializableBundler
        }
        if (Parcelable::class.java.isAssignableFrom(clazz)) {
            return parcelableBundler
        }
        throw InvalidParameterException("Only Serializable and Parcelable are supported")
    }

    fun <T : Any> state(instance: Any,
                        value: T,
                        bundler: Bundler,
                        beforeChange: ((T, T) -> Boolean)?,
                        afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return SavedProperty(bundler, value, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }

    fun <T : Any> state(instance: Any,
                        value: T,
                        beforeChange: ((T, T) -> Boolean)?,
                        afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return state(instance, value, bundlerForClass(value.javaClass), beforeChange, afterChange)
    }

    fun <T> nullableState(instance: Any,
                          bundler: Bundler,
                          beforeChange: ((T?, T?) -> Boolean)?,
                          afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
        return NullableSavedProperty(bundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }

    fun <T> nullableState(instance: Any,
                          clazz: Class<T>,
                          beforeChange: ((T?, T?) -> Boolean)?,
                          afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
        return nullableState(instance, bundlerForClass(clazz), beforeChange, afterChange)
    }

    fun <T> lateState(instance: Any,
                      bundler: Bundler,
                      beforeChange: ((T?, T) -> Boolean)?,
                      afterChange: ((T?, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return LateSavedProperty(bundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }

    fun <T> lateState(instance: Any,
                      clazz: Class<T>,
                      beforeChange: ((T?, T) -> Boolean)?,
                      afterChange: ((T?, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return lateState(instance, bundlerForClass(clazz), beforeChange, afterChange)
    }

    fun freezeInstanceState(instance: Any, outState: Bundle) {
        val savedProperties = IceKick.savedInstances[instance] ?: return
        savedProperties.forEachIndexed { index, property ->
            property.save(outState, "$index")
        }
    }

    fun unfreezeInstanceState(instance: Any, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            return
        }

        val savedProperties = IceKick.savedInstances[instance] ?: return
        savedProperties.forEachIndexed { index, property ->
            property.load(savedInstanceState, "$index")
        }
    }
}
