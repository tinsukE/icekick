package com.tinsuke.icekick.bundler

import android.os.Bundle

interface Bundler {
    fun save(bundle: Bundle, key: String, value: Any?)
    fun load(bundle: Bundle, key: String): Any?
}
