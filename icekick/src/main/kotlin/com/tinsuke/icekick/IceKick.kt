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

    fun <S : Serializable> state(instance: Any, onSet: ((S?) -> Unit)? = null) = NullableSavedProperty<Any, S>(onSet).apply {
        createOrGetSavedProperties(instance).nullableProperties.add(this)
    }

    fun <S : Serializable> state(instance: Any, value: S, onSet: ((S) -> Unit)? = null) = SavedProperty<Any, S>(value, onSet).apply {
        createOrGetSavedProperties(instance).properties.add(this)
    }

    fun <S : Serializable> lateState(instance: Any, onSet: ((S) -> Unit)? = null) = LateinitSavedProperty<Any, S>(onSet).apply {
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

fun <S : Serializable> Activity.state(onSet: ((S?) -> Unit)? = null): NullableSavedProperty<Any, S> = IceKick.state(this, onSet)
fun <S : Serializable> Activity.state(value: S, onSet: ((S) -> Unit)? = null): SavedProperty<Any, S> = IceKick.state(this, value, onSet)
fun <S : Serializable> Activity.lateState(onSet: ((S) -> Unit)? = null): LateinitSavedProperty<Any, S> = IceKick.lateState(this, onSet)
fun Activity.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Activity.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> Fragment.state(onSet: ((S?) -> Unit)? = null): NullableSavedProperty<Any, S> = IceKick.state(this, onSet)
fun <S : Serializable> Fragment.state(value: S, onSet: ((S) -> Unit)? = null): SavedProperty<Any, S> = IceKick.state(this, value, onSet)
fun <S : Serializable> Fragment.lateState(onSet: ((S) -> Unit)? = null): LateinitSavedProperty<Any, S> = IceKick.lateState(this, onSet)
fun Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> android.support.v4.app.Fragment.state(onSet: ((S?) -> Unit)? = null): NullableSavedProperty<Any, S> = IceKick.state(this, onSet)
fun <S : Serializable> android.support.v4.app.Fragment.state(value: S, onSet: ((S) -> Unit)? = null): SavedProperty<Any, S> = IceKick.state(this, value, onSet)
fun <S : Serializable> android.support.v4.app.Fragment.lateState(onSet: ((S) -> Unit)? = null): LateinitSavedProperty<Any, S> = IceKick.lateState(this, onSet)
fun android.support.v4.app.Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun android.support.v4.app.Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> View.state(onSet: ((S?) -> Unit)? = null): NullableSavedProperty<Any, S> = IceKick.state(this, onSet)
fun <S : Serializable> View.state(value: S, onSet: ((S) -> Unit)? = null): SavedProperty<Any, S> = IceKick.state(this, value, onSet)
fun <S : Serializable> View.lateState(onSet: ((S) -> Unit)? = null): LateinitSavedProperty<Any, S> = IceKick.lateState(this, onSet)

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

