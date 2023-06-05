package com.mockeytest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mockeytest.adapters.ProductAdapter
import com.mockeytest.databinding.ActivityFavouriteBinding
import com.mockeytest.databinding.ActivityMainBinding
import com.mockeytest.model.Product
import com.mockeytest.model.ProductFavourite
import com.mockeytest.ui.ProductViewModel
import com.mockeytest.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var _binding: ActivityFavouriteBinding? = null
    private val binding get() = _binding!!
    private val productViewModel by viewModels<ProductViewModel>()

    private lateinit var adapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        _binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter(::onNoteClicked,::onFavouriteClicked,::onFavouriteRemoved)

        binding.productList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.productList.adapter = adapter
        bindObservers()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.favourite
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            bottomNavigationView.postDelayed({
                when (item.itemId) {
                    R.id.all -> {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        overridePendingTransition(0, 0)
                        finish()
                    }
                    R.id.favourite -> {}
                }
            }, 10)
            false
        }
    }

    override fun onResume() {
        super.onResume()
        productViewModel.getFavouriteProduct()
    }

    private fun onNoteClicked(id: String){
        val intent = Intent(applicationContext, DetailPageActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
    private fun onFavouriteRemoved(id:String) {
        productViewModel.onRemovedFavourite(id)
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
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //Log.d(TAG, "onCreate: ${it.data}")
                    adapter.submitList(it.data!!.products)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
