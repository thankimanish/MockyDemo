package com.mockeytest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mockeytest.di.ApplicationScope
import com.mockeytest.model.Converters
import com.mockeytest.model.Product
import com.mockeytest.model.ProductFavourite
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Product::class,ProductFavourite::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
    class Callback @Inject constructor(
        private val database: Provider<ProductDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}