package com.mockeytest.api

import com.mockeytest.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.*

interface ProductAPI {

    @GET("2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getProducts(): Response<ProductsResponse>

}