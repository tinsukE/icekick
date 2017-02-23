package com.tinsuke.icekick.bundler

import android.os.Bundle
import android.os.Parcelable

internal class ParcelableBundler : Bundler<Parcelable> {
    override fun save(bundle: Bundle, key: String, value: Parcelable?) = bundle.putParcelable(key, value as? Parcelable)
    override fun load(bundle: Bundle, key: String) = bundle.getParcelable<Parcelable>(key)
}
