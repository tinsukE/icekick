package com.tinsuke.icekick.bundler

import android.os.Bundle
import android.os.Parcelable

internal class ParcelableBundler : Bundler {
    override fun save(bundle: Bundle, key: String, value: Any?) = bundle.putParcelable(key, value as? Parcelable)
    override fun load(bundle: Bundle, key: String): Any? = bundle.getParcelable(key)
}
