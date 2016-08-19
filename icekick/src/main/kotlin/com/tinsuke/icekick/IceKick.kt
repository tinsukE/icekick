package com.tinsuke.icekick

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import java.io.Serializable
import java.util.*

private class IceKick {

    companion object {

        data class SavedProperties(
                val nullableProperties: MutableList<NullableSavedProperty<*, *>> = LinkedList(),
                val properties: MutableList<SavedProperty<*, *>> = LinkedList()
        )

        val savedInstances = WeakHashMap<Any, SavedProperties>()

        private fun createSavedProperties(instance: Any): SavedProperties {
            var savedProperties = savedInstances[instance]
            if (savedProperties == null) {
                savedProperties = SavedProperties()
                savedInstances[instance] = savedProperties
            }
            return savedProperties
        }

        fun <S : Serializable> state(instance: Any): NullableSavedProperty<Any, S> {
            val savedProperties = createSavedProperties(instance)
            val property = NullableSavedProperty<Any, S>()
            savedProperties.nullableProperties.add(property)
            return property
        }

        fun <S : Serializable> state(instance: Any, value: S): SavedProperty<Any, S> {
            val savedProperties = createSavedProperties(instance)
            val property = SavedProperty<Any, S>(value)
            savedProperties.properties.add(property)
            return property
        }

        fun freezeInstanceState(instance: Any, outState: Bundle) {
            val savedProperties = IceKick.savedInstances[instance] ?: return
            for ((index, property) in savedProperties.nullableProperties.withIndex()) {
                outState.putSerializable("iceKick_nullable_$index", property.value)
            }
            for ((index, property) in savedProperties.properties.withIndex()) {
                outState.putSerializable("iceKick_$index", property.value)
            }
        }

        fun unfreezeInstanceState(instance: Any, savedInstanceState: Bundle?) {
            if (savedInstanceState == null) {
                return
            }

            val savedProperties = IceKick.savedInstances[instance] ?: return
            for ((index, property) in savedProperties.nullableProperties.withIndex()) {
                property.value = savedInstanceState.getSerializable("iceKick_nullable_$index")
            }
            for ((index, property) in savedProperties.properties.withIndex()) {
                property.value = savedInstanceState.getSerializable("iceKick_$index") ?: continue
            }
        }

    }

}

fun <S : Serializable> Activity.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> Activity.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun Activity.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Activity.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> Fragment.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> Fragment.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> android.support.v4.app.Fragment.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> android.support.v4.app.Fragment.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)
fun android.support.v4.app.Fragment.freezeInstanceState(outState: Bundle) = IceKick.freezeInstanceState(this, outState)
fun android.support.v4.app.Fragment.unfreezeInstanceState(savedInstanceState: Bundle?) = IceKick.unfreezeInstanceState(this, savedInstanceState)

fun <S : Serializable> View.state(): NullableSavedProperty<Any, S> = IceKick.state(this)
fun <S : Serializable> View.state(value: S): SavedProperty<Any, S> = IceKick.state(this, value)

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

