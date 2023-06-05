package com.mockeytest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mockeytest.R
import com.mockeytest.databinding.ProductItemBinding
import com.mockeytest.model.Product
import com.mockeytest.ui.ProductViewModel

class ProductAdapter(
    private val onNoteClicked: (String) -> Unit,
    private val onFavouriteClicked: (Product) -> Unit,
    private val onFavouriteRemoved: (String) -> Unit
) :
    ListAdapter<Product, ProductAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val product = getItem(position)
        product?.let {
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productname.text = product.title
            binding.productprice.text = product.price[0].value.toString()
            if(product.isFavourite){
                binding.productheart.load(R.drawable.ic_heart)
            }else{
                binding.productheart.load(R.drawable.ic_favorite_border)
            }
            binding.productimage.load(product.imageURL) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            binding.productheart.setOnClickListener{
                if(product.isFavourite) {
                    onFavouriteRemoved(product.id)
                }else{
                    onFavouriteClicked(product)
                }
            }
            binding.root.setOnClickListener {
                onNoteClicked(product.id)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}