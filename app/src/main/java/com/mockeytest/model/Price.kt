package com.mockeytest.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    @PrimaryKey
    val isOfferPrice: Boolean,
    val message: String,
    val value: Double
) : Parcelable