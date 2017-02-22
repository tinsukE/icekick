package com.tinsuke.icekick.extension

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

internal class ViewState(var state: Bundle?, var parcelable: Parcelable?) : Parcelable {
    constructor(source: Parcel) : this(source.readBundle(), source.readParcelable(null))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeBundle(state)
        dest.writeParcelable(parcelable, flags)
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<ViewState> {
            override fun createFromParcel(source: Parcel) = ViewState(source)
            override fun newArray(size: Int) = arrayOfNulls<ViewState>(size)
        }
    }
}