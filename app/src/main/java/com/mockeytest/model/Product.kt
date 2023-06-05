package com.mockeytest.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @NonNull
    val id: String,
    val addToCartButtonText: String?,
    val badges: List<String>,
    val brand: String?,
    val citrusId: String?,
    val imageURL: String?,
    val isAddToCartEnable: Boolean,
    val isDeliveryOnly: Boolean,
    val isDirectFromSupplier: Boolean,
    val isFindMeEnable: Boolean,
    val isInTrolley: Boolean,
    val isInWishlist: Boolean,
    val messages: Messages,
    val price: List<Price>,
    val purchaseTypes: List<PurchaseType>,
    val ratingCount: Double,
    val saleUnitPrice: Double,
    val title: String?,
    val totalReviewCount: Int,
    val isFavourite: Boolean
) : Parcelable
class Converters {

    @TypeConverter
    fun listToJsonPrice(value: List<Price>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListPrice(value: String) = Gson().fromJson(value, Array<Price>::class.java).toList()

    @TypeConverter
    fun listToJsonPurchaseType(value: List<PurchaseType>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListPurchaseType(value: String) = Gson().fromJson(value, Array<PurchaseType>::class.java).toList()

    @TypeConverter
    fun listToJsonMessages(value: Messages?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListMessages(value: String) = Gson().fromJson(value, Messages::class.java)

    @TypeConverter
    fun listToJsonBadges(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListBadges(value: String?) = Gson().fromJson(value, Array<String>::class.java).toList()
}