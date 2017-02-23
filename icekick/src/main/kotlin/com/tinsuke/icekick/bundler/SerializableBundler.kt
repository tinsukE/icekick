package com.tinsuke.icekick.bundler

import android.os.Bundle
import java.io.Serializable

internal class SerializableBundler : Bundler<Serializable> {
    override fun save(bundle: Bundle, key: String, value: Serializable?) = bundle.putSerializable(key, value as? Serializable)
    override fun load(bundle: Bundle, key: String) = bundle.getSerializable(key)
}
