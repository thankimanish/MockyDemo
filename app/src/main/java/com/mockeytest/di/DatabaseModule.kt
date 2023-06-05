package com.mockeytest.di

import android.app.Application
import androidx.room.Room
import com.mockeytest.db.ProductDao
import com.mockeytest.db.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: ProductDatabase.Callback): ProductDatabase{
        return Room.databaseBuilder(application, ProductDatabase::class.java, "product_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: ProductDatabase): ProductDao {
        return db.productDao()
    }
}