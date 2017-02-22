package com.tinsuke.icekick.property

import android.os.Bundle
import kotlin.properties.ReadWriteProperty

internal abstract class BaseSavedProperty<T> : ReadWriteProperty<Any, T> {
    abstract fun save(bundle: Bundle, key: String)
    abstract fun load(bundle: Bundle, key: String)
}
