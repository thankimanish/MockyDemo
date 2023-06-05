package com.mockeytest.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PurchaseType(
    @PrimaryKey
    val cartQty: Int,
    val displayName: String?,
    val maxQtyLimit: Int,
    val minQtyLimit: Int,
    val purchaseType: String?,
    val unitPrice: Double
) : Parcelable