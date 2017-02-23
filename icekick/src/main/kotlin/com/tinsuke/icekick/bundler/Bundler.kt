package com.tinsuke.icekick.bundler

import android.os.Bundle

interface Bundler<T> {
    fun save(bundle: Bundle, key: String, value: T?)
    fun load(bundle: Bundle, key: String): T?
}
