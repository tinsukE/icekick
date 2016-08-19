package com.tinsuke.icekick

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import java.io.Serializable
import java.util.*

object IceKick {
    private const val PROPERTY_PREFIX = "iceKick_"
    private const val NULLABLE_PREFIX = "iceKick_nullable_"
    private const val LATEINIT_PREFIX = "iceKick_lateinit_"

    internal data class SavedProperties(
            val properties: MutableList<SavedProperty<*, *>> = mutableListOf(),
            val nullableProperties: MutableList<NullableSavedProperty<*, *>> = mutableListOf(),
            val lateinitProperties: MutableList<LateinitSavedProperty<*, *>> = mutableListOf()
    )

    internal val savedInstances = WeakHashMap<Any, SavedProperties>()

    private fun createOrGetSavedProperties(instance: Any) = savedInstances.getOrPut(instance) { SavedProperties() }

    fun <S : Serializable> state(instance: Any) = NullableSavedProperty<Any, S>().apply {
        createOrGetSavedProperties(instance).nullableProperties.add(this)
    }

    fun <S : Serializable> state(instance: Any, value: S) = SavedProperty<Any, S>(value).apply {
        createOrGetSavedProperties(instance).properties.add(this)
    }

    fun <S : Serializable> latestate(instance: Any) = LateinitSavedProperty<Any, S>().apply {
        createOrGetSavedProperties(instance).lateinitProperties.add(this)
    }

    fun freezeInstanceState(instance: Any, outState: Bundle) {
        val savedProperties = IceKick.savedInstances[instance] ?: return
        savedProperties.properties.forEachIndexed { index, property ->
            outState.putSerializable("$PROPERTY_PREFIX$index", property.value)
        }
        savedProperties.nullableProperties.forEachIndexed { index, property ->
            outState.putSerializable("$NULLABLE_PREFIX$index", property.value)
        }
        savedProperties.lateinitProperties.forEachIndexed { index, property ->
            outState.putSerializable("$LATEINIT_PREFIX$index", property.value)
        }
    }

    fun unfreezeInstanceState(instance: Any, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            return
        }

        val savedProperties = IceKick.savedInstances[instance] ?: return
        savedProperties.properties.forEachIndexed { index, property ->
            property.value = savedInstanceState.getSerializable("$PROPERTY_PREFIX$index") ?: return@forEachIndexed
        }
        savedProperties.nullableProperties.forEachIndexed { index, property ->
            property.value = savedInstanceState.getSerializable("$NULLABLE_PREFIX$index")
        }
        savedProperties.lateinitProperties.forEachIndexed { index, property ->
            property.value = savedInstanceState.getSerializable("$LATEINIT_PREFIX$index") ?: return@forEachIndexed
        }
    }
}

fun <S : Serializable> Activity.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> Activity.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun <S : Serializable> Activity.latestate(): LateinitSavedProperty<Any, S> = IceKick.latestate(this)
fun Activity.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Activity.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> Fragment.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> Fragment.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun <S : Serializable> Fragment.latestate(): LateinitSavedProperty<Any, S> = IceKick.latestate(this)
fun Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> android.support.v4.app.Fragment.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> android.support.v4.app.Fragment.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun <S : Serializable> android.support.v4.app.Fragment.latestate(): LateinitSavedProperty<Any, S> = IceKick.latestate(this)
fun android.support.v4.app.Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun android.support.v4.app.Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> View.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> View.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun <S : Serializable> View.latestate(): LateinitSavedProperty<Any, S> = IceKick.latestate(this)

fun View.freezeInstanceState(parcelable: Parcelable?): Parcelable? {
    if (IceKick.savedInstances[this] == null) {
        return parcelable
    }

    val state = Bundle()
    IceKick.freezeInstanceState(this, state)
    return ViewState(state, parcelable)
}

fun View.unfreezeInstanceState(parcelable: Parcelable): Parcelable? {
    if (IceKick.savedInstances[this] == null || parcelable !is ViewState) {
        return parcelable
    }

    IceKick.unfreezeInstanceState(this, parcelable.state)
    return parcelable.parcelable
}

