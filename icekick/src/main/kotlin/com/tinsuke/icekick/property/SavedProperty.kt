package com.tinsuke.icekick.property

import android.os.Bundle
import com.tinsuke.icekick.bundler.Bundler
import kotlin.reflect.KProperty

internal class SavedProperty<T>(var bundler: Bundler,
                                var value: T,
                                var beforeChange: ((T, T) -> Boolean)? = null,
                                var afterChange: ((T, T) -> Unit)? = null) : BaseSavedProperty<T>() {

    override fun getValue(thisRef: Any, property: KProperty<*>) = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val oldValue = this.value

        if (beforeChange?.invoke(oldValue, value) == false) {
            return
        }
        this.value = value
        afterChange?.invoke(oldValue, value)
    }

    override fun save(bundle: Bundle, key: String) = bundler.save(bundle, key, value)

    override fun load(bundle: Bundle, key: String) {
        @Suppress("UNCHECKED_CAST")
        value = bundler.load(bundle, key) as T
    }

}
