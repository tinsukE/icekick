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
import java.util.*
import kotlin.properties.ReadWriteProperty

internal object IceKick {

    val savedInstances = WeakHashMap<Any, MutableList<BaseSavedProperty<*>>>()

    private val serializableBundler by lazy { SerializableBundler() }
    private val parcelableBundler by lazy { ParcelableBundler() }

    private fun getOrPutSavedProperties(instance: Any) = savedInstances.getOrPut(instance) { mutableListOf() }

    fun <T> state(instance: Any,
                  value: T,
                  bundler: Bundler<T>,
                  beforeChange: ((T, T) -> Boolean)?,
                  afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return SavedProperty(bundler, value, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }
    fun <T : Serializable> serialState(instance: Any,
                                       value: T,
                                       beforeChange: ((T, T) -> Boolean)?,
                                       afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return SavedProperty(serializableBundler, value, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }
    fun <T : Parcelable> parcelState(instance: Any,
                                     value: T,
                                     beforeChange: ((T, T) -> Boolean)?,
                                     afterChange: ((T, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return SavedProperty(parcelableBundler, value, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }

    fun <T> state(instance: Any,
                  bundler: Bundler<T>,
                  beforeChange: ((T?, T?) -> Boolean)?,
                  afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
        return NullableSavedProperty(bundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }
    fun <T : Serializable> serialState(instance: Any,
                                       beforeChange: ((T?, T?) -> Boolean)?,
                                       afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
        return NullableSavedProperty(serializableBundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }
    fun <T : Parcelable> parcelState(instance: Any,
                                     beforeChange: ((T?, T?) -> Boolean)?,
                                     afterChange: ((T?, T?) -> Unit)?): ReadWriteProperty<Any, T?> {
        return NullableSavedProperty(parcelableBundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }

    fun <T> lateState(instance: Any,
                      bundler: Bundler<T>,
                      beforeChange: ((T?, T) -> Boolean)?,
                      afterChange: ((T?, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return LateSavedProperty(bundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }
    fun <T : Serializable> serialLateState(instance: Any,
                                           beforeChange: ((T?, T) -> Boolean)?,
                                           afterChange: ((T?, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return LateSavedProperty(serializableBundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
    }
    fun <T : Parcelable> parcelLateState(instance: Any,
                                         beforeChange: ((T?, T) -> Boolean)?,
                                         afterChange: ((T?, T) -> Unit)?): ReadWriteProperty<Any, T> {
        return LateSavedProperty(parcelableBundler, beforeChange, afterChange).apply {
            getOrPutSavedProperties(instance).add(this)
        }
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
