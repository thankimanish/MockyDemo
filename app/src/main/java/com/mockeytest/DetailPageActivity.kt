package com.mockeytest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mockeytest.adapters.ProductAdapter
import com.mockeytest.databinding.ActivityDetailpageBinding
import com.mockeytest.databinding.ActivityMainBinding
import com.mockeytest.model.Product
import com.mockeytest.model.ProductFavourite
import com.mockeytest.ui.ProductViewModel
import com.mockeytest.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPageActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var _binding: ActivityDetailpageBinding? = null
    private val binding get() = _binding!!
    private val productViewModel by viewModels<ProductViewModel>()
    private var id:String?=null
    var isFavourite:Boolean = false
    var datalocal:Product?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailpage)
        _binding = ActivityDetailpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObservers()
        id = intent.getStringExtra("id").toString()
        productViewModel.getProductById(id!!)

        binding.productheart.setOnClickListener(View.OnClickListener {
            if(isFavourite){
                id?.let { it1 -> onFavouriteRemoved(it1) }
            }else{
                datalocal?.let { it1 -> onFavouriteClicked(it1) }
            }
            productViewModel.getProductById(id!!)
        })
    }
    private fun onFavouriteRemoved(id:String){
        productViewModel.onRemovedFavouriteFromDetailPage(id)
    }
    private fun onFavouriteClicked(noteResponse: Product){
        var product = ProductFavourite(
            id = noteResponse.id,
            addToCartButtonText=noteResponse.addToCartButtonText,
            badges=noteResponse.badges,
            brand=noteResponse.brand,
            citrusId= noteResponse.citrusId,
            imageURL=noteResponse.imageURL,
            isAddToCartEnable=noteResponse.isAddToCartEnable,
            isDeliveryOnly=noteResponse.isDeliveryOnly,
            isDirectFromSupplier=noteResponse.isDirectFromSupplier,
            isFindMeEnable=noteResponse.isFindMeEnable,
            isInTrolley=noteResponse.isInTrolley,
            isInWishlist=noteResponse.isInWishlist,
            messages=noteResponse.messages,
            price=noteResponse.price,
            purchaseTypes=noteResponse.purchaseTypes,
            ratingCount=noteResponse.ratingCount,
            saleUnitPrice=noteResponse.saleUnitPrice,
            title=noteResponse.title,
            totalReviewCount=noteResponse.totalReviewCount,
            isFavourite = true)
        productViewModel.onProductFavourite(product)
    }
    private fun bindObservers() {
        productViewModel.productsLiveData.observe(this, Observer {
            when (it) {

                is NetworkResult.Success -> {
                    datalocal = it.data!!.products[0]
                    binding.productname.text = it.data!!.products[0].title
                    binding.productprice.text = it.data!!.products[0].price[0].value.toString()
                    if(it.data.products[0].isFavourite){
                        binding.productheart.load(R.drawable.ic_heart)
                        isFavourite = true
                    }else{
                        binding.productheart.load(R.drawable.ic_favorite_border)
                        isFavourite = false
                    }
                    binding.productimage.load(it.data.products[0].imageURL) {
                        crossfade(600)
                        error(R.drawable.ic_error_placeholder)
                    }

                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
