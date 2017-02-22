package com.tinsuke.icekick.bundler

import android.os.Bundle
import java.io.Serializable

internal class SerializableBundler : Bundler {
    override fun save(bundle: Bundle, key: String, value: Any?) = bundle.putSerializable(key, value as? Serializable)
    override fun load(bundle: Bundle, key: String): Any? = bundle.getSerializable(key)
}
