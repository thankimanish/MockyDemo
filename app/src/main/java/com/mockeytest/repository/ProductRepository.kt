package com.mockeytest.repository

import androidx.lifecycle.MutableLiveData
import com.mockeytest.api.ProductAPI
import com.mockeytest.db.ProductDao
import com.mockeytest.model.Messages
import com.mockeytest.model.Price
import com.mockeytest.model.Product
import com.mockeytest.model.ProductFavourite
import com.mockeytest.model.ProductsResponse
import com.mockeytest.model.PurchaseType

import com.mockeytest.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productAPI: ProductAPI, private val productDao: ProductDao,) {

    private val _productsLiveData = MutableLiveData<NetworkResult<ProductsResponse>>()
    val productsLiveData get() = _productsLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun deleteFromFavourite(id:String){
       productDao.deleteData(id)
        getFavouriteProducts()
    }

    suspend fun deleteFromAll(id:String){
        productDao.deleteData(id)
        getProducts()
    }

    suspend fun deleteFromDetailPage(id:String){
        productDao.deleteData(id)
        getProductsById(id)
    }

    suspend fun addProductsFavourite(products: ProductFavourite){
        productDao.addProductsFavourite(products)
        val productsResponseLocal: ProductsResponse = ProductsResponse(productDao.getProducts())
        val productsLocal = productsResponseLocal.products.mapNotNull { productResponse ->
            if (productResponse.id != null) {
                if(productDao.isFavorite(productResponse.id)){
                    Product(
                        id = productResponse.id,
                        addToCartButtonText=productResponse.addToCartButtonText,
                        badges=productResponse.badges,
                        brand=productResponse.brand,
                        citrusId= productResponse.citrusId,
                        imageURL=productResponse.imageURL,
                        isAddToCartEnable=productResponse.isAddToCartEnable,
                        isDeliveryOnly=productResponse.isDeliveryOnly,
                        isDirectFromSupplier=productResponse.isDirectFromSupplier,
                        isFindMeEnable=productResponse.isFindMeEnable,
                        isInTrolley=productResponse.isInTrolley,
                        isInWishlist=productResponse.isInWishlist,
                        messages=productResponse.messages,
                        price=productResponse.price,
                        purchaseTypes=productResponse.purchaseTypes,
                        ratingCount=productResponse.ratingCount,
                        saleUnitPrice=productResponse.saleUnitPrice,
                        title=productResponse.title,
                        totalReviewCount=productResponse.totalReviewCount,
                        isFavourite = true)
                }else{
                    Product(
                        id = productResponse.id,
                        addToCartButtonText=productResponse.addToCartButtonText,
                        badges=productResponse.badges,
                        brand=productResponse.brand,
                        citrusId= productResponse.citrusId,
                        imageURL=productResponse.imageURL,
                        isAddToCartEnable=productResponse.isAddToCartEnable,
                        isDeliveryOnly=productResponse.isDeliveryOnly,
                        isDirectFromSupplier=productResponse.isDirectFromSupplier,
                        isFindMeEnable=productResponse.isFindMeEnable,
                        isInTrolley=productResponse.isInTrolley,
                        isInWishlist=productResponse.isInWishlist,
                        messages=productResponse.messages,
                        price=productResponse.price,
                        purchaseTypes=productResponse.purchaseTypes,
                        ratingCount=productResponse.ratingCount,
                        saleUnitPrice=productResponse.saleUnitPrice,
                        title=productResponse.title,
                        totalReviewCount=productResponse.totalReviewCount,
                        isFavourite = false)
                }
            } else {
                null // Skip the product if the id is null
            }
        }
        _productsLiveData.postValue(NetworkResult.Success(ProductsResponse(productsLocal)))
    }

    suspend fun getProducts() {
        _productsLiveData.postValue(NetworkResult.Loading())
        val response = productAPI.getProducts()
        if (response.isSuccessful && response.body() != null) {
            val productsResponse: ProductsResponse = response.body()!!
            val products = productsResponse.products.mapNotNull { productResponse ->
                if (productResponse.id != null) {
                    Product(
                    id = productResponse.id,
                    addToCartButtonText=productResponse.addToCartButtonText,
                    badges=productResponse.badges,
                    brand=productResponse.brand,
                    citrusId= productResponse.citrusId,
                    imageURL=productResponse.imageURL,
                    isAddToCartEnable=productResponse.isAddToCartEnable,
                    isDeliveryOnly=productResponse.isDeliveryOnly,
                    isDirectFromSupplier=productResponse.isDirectFromSupplier,
                    isFindMeEnable=productResponse.isFindMeEnable,
                    isInTrolley=productResponse.isInTrolley,
                    isInWishlist=productResponse.isInWishlist,
                    messages=productResponse.messages,
                    price=productResponse.price,
                    purchaseTypes=productResponse.purchaseTypes,
                    ratingCount=productResponse.ratingCount,
                    saleUnitPrice=productResponse.saleUnitPrice,
                    title=productResponse.title,
                    totalReviewCount=productResponse.totalReviewCount,
                    isFavourite=false)
                } else {
                    null // Skip the product if the id is null
                }
            }
            productDao.addProducts(products)
            //productDao.addProducts(response.body()!!.products)

            val productsResponseLocal: ProductsResponse = ProductsResponse(productDao.getProducts())
            val productsLocal = productsResponseLocal.products.mapNotNull { productResponse ->
                if (productResponse.id != null) {
                    if(productDao.isFavorite(productResponse.id)){
                        Product(
                            id = productResponse.id,
                            addToCartButtonText=productResponse.addToCartButtonText,
                            badges=productResponse.badges,
                            brand=productResponse.brand,
                            citrusId= productResponse.citrusId,
                            imageURL=productResponse.imageURL,
                            isAddToCartEnable=productResponse.isAddToCartEnable,
                            isDeliveryOnly=productResponse.isDeliveryOnly,
                            isDirectFromSupplier=productResponse.isDirectFromSupplier,
                            isFindMeEnable=productResponse.isFindMeEnable,
                            isInTrolley=productResponse.isInTrolley,
                            isInWishlist=productResponse.isInWishlist,
                            messages=productResponse.messages,
                            price=productResponse.price,
                            purchaseTypes=productResponse.purchaseTypes,
                            ratingCount=productResponse.ratingCount,
                            saleUnitPrice=productResponse.saleUnitPrice,
                            title=productResponse.title,
                            totalReviewCount=productResponse.totalReviewCount,
                            isFavourite = true)
                    }else{
                        Product(
                            id = productResponse.id,
                            addToCartButtonText=productResponse.addToCartButtonText,
                            badges=productResponse.badges,
                            brand=productResponse.brand,
                            citrusId= productResponse.citrusId,
                            imageURL=productResponse.imageURL,
                            isAddToCartEnable=productResponse.isAddToCartEnable,
                            isDeliveryOnly=productResponse.isDeliveryOnly,
                            isDirectFromSupplier=productResponse.isDirectFromSupplier,
                            isFindMeEnable=productResponse.isFindMeEnable,
                            isInTrolley=productResponse.isInTrolley,
                            isInWishlist=productResponse.isInWishlist,
                            messages=productResponse.messages,
                            price=productResponse.price,
                            purchaseTypes=productResponse.purchaseTypes,
                            ratingCount=productResponse.ratingCount,
                            saleUnitPrice=productResponse.saleUnitPrice,
                            title=productResponse.title,
                            totalReviewCount=productResponse.totalReviewCount,
                            isFavourite = false)
                    }
                } else {
                    null // Skip the product if the id is null
                }
            }
            _productsLiveData.postValue(NetworkResult.Success(ProductsResponse(productsLocal)))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _productsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _productsLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun getFavouriteProducts() {
        val productsResponseLocal: ProductsResponse = ProductsResponse(productDao.getFavouriteProducts())
        val productsLocal = productsResponseLocal.products.mapNotNull { productResponse ->
            if (productResponse.id != null) {
                Product(
                    id = productResponse.id,
                    addToCartButtonText=productResponse.addToCartButtonText,
                    badges=productResponse.badges,
                    brand=productResponse.brand,
                    citrusId= productResponse.citrusId,
                    imageURL=productResponse.imageURL,
                    isAddToCartEnable=productResponse.isAddToCartEnable,
                    isDeliveryOnly=productResponse.isDeliveryOnly,
                    isDirectFromSupplier=productResponse.isDirectFromSupplier,
                    isFindMeEnable=productResponse.isFindMeEnable,
                    isInTrolley=productResponse.isInTrolley,
                    isInWishlist=productResponse.isInWishlist,
                    messages=productResponse.messages,
                    price=productResponse.price,
                    purchaseTypes=productResponse.purchaseTypes,
                    ratingCount=productResponse.ratingCount,
                    saleUnitPrice=productResponse.saleUnitPrice,
                    title=productResponse.title,
                    totalReviewCount=productResponse.totalReviewCount,
                    isFavourite = true)
            } else {
                null // Skip the product if the id is null
            }
        }
        _productsLiveData.postValue(NetworkResult.Success(ProductsResponse(productsLocal)))
    }

    suspend fun getProductsById(id:String) {
        val productsResponseLocal: ProductsResponse = ProductsResponse(productDao.getProductById(id))
        val productsLocal = productsResponseLocal.products.mapNotNull { productResponse ->
            if (productResponse.id != null) {
                if(productDao.isFavorite(productResponse.id)){
                    Product(
                        id = productResponse.id,
                        addToCartButtonText=productResponse.addToCartButtonText,
                        badges=productResponse.badges,
                        brand=productResponse.brand,
                        citrusId= productResponse.citrusId,
                        imageURL=productResponse.imageURL,
                        isAddToCartEnable=productResponse.isAddToCartEnable,
                        isDeliveryOnly=productResponse.isDeliveryOnly,
                        isDirectFromSupplier=productResponse.isDirectFromSupplier,
                        isFindMeEnable=productResponse.isFindMeEnable,
                        isInTrolley=productResponse.isInTrolley,
                        isInWishlist=productResponse.isInWishlist,
                        messages=productResponse.messages,
                        price=productResponse.price,
                        purchaseTypes=productResponse.purchaseTypes,
                        ratingCount=productResponse.ratingCount,
                        saleUnitPrice=productResponse.saleUnitPrice,
                        title=productResponse.title,
                        totalReviewCount=productResponse.totalReviewCount,
                        isFavourite = true)
                }else{
                    Product(
                        id = productResponse.id,
                        addToCartButtonText=productResponse.addToCartButtonText,
                        badges=productResponse.badges,
                        brand=productResponse.brand,
                        citrusId= productResponse.citrusId,
                        imageURL=productResponse.imageURL,
                        isAddToCartEnable=productResponse.isAddToCartEnable,
                        isDeliveryOnly=productResponse.isDeliveryOnly,
                        isDirectFromSupplier=productResponse.isDirectFromSupplier,
                        isFindMeEnable=productResponse.isFindMeEnable,
                        isInTrolley=productResponse.isInTrolley,
                        isInWishlist=productResponse.isInWishlist,
                        messages=productResponse.messages,
                        price=productResponse.price,
                        purchaseTypes=productResponse.purchaseTypes,
                        ratingCount=productResponse.ratingCount,
                        saleUnitPrice=productResponse.saleUnitPrice,
                        title=productResponse.title,
                        totalReviewCount=productResponse.totalReviewCount,
                        isFavourite = false)
                }
            } else {
                null // Skip the product if the id is null
            }
        }
        _productsLiveData.postValue(NetworkResult.Success(ProductsResponse(productsLocal)))
    }

    private fun handleResponse(response: Response<ProductsResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(Pair(true, message)))
        } else {
            _statusLiveData.postValue(NetworkResult.Success(Pair(false, "Something went wrong")))
        }
    }
}