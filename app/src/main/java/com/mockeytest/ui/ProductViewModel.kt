package com.mockeytest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mockeytest.model.Product
import com.mockeytest.model.ProductFavourite
import com.mockeytest.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val ProductRepository: ProductRepository) : ViewModel() {

    val productsLiveData get() = ProductRepository.productsLiveData

    fun getAllNotes() {
        viewModelScope.launch {
            ProductRepository.getProducts()
        }
    }

    fun getProductById(id: String) {
        viewModelScope.launch {
            ProductRepository.getProductsById(id)
        }
    }

    fun getFavouriteProduct() {
        viewModelScope.launch {
            ProductRepository.getFavouriteProducts()
        }
    }

    fun onProductFavourite(products: ProductFavourite) {
        viewModelScope.launch {
            ProductRepository.addProductsFavourite(products)
        }
    }

    fun onRemovedFavouriteAll(id: String) {
        viewModelScope.launch {
            ProductRepository.deleteFromAll(id)
        }
    }

    fun onRemovedFavourite(id: String) {
        viewModelScope.launch {
            ProductRepository.deleteFromFavourite(id)
        }
    }
    fun onRemovedFavouriteFromDetailPage(id: String) {
        viewModelScope.launch {
            ProductRepository.deleteFromDetailPage(id)
        }
    }

}