package com.mockeytest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Messages(
    val promotionalMessage: String?,
    val sash: Sash,
    val secondaryMessage: String?
) : Parcelable