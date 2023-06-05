package com.mockeytest.db

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mockeytest.model.Product
import com.mockeytest.model.ProductFavourite
import com.mockeytest.model.ProductsResponse
import retrofit2.Response

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(quotes: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductsFavourite(quotes: ProductFavourite)

    @Query("SELECT * FROM product")
    suspend fun getProducts() : List<Product>

    @Query("SELECT * FROM product_favourite")
    suspend fun getFavouriteProducts() : List<Product>

    @Query("SELECT EXISTS (SELECT 1 FROM product_favourite WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean

    @Query("DELETE FROM product_favourite WHERE id = :id")
    suspend fun deleteData(id: String)

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id:String) : List<Product>
}