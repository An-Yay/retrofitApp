package com.example.retrofitapp

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView

    
class My_Adapter:ListAdapter<item_data, My_Adapter.ItemViewHolder>(DiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): My_Adapter.ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<item_data>(){
        override fun areItemsTheSame(oldItem: item_data, newItem:item_data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: item_data, newItem: item_data): Boolean {
            return oldItem == newItem
        }
    }

    class ItemViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: item_data){
            binding.apply {
                itemTitle.text = item.user.name
                itemAddress.text = item.user.location
                itemDesc.text = item.user.bio

                Glide.with(itemImageView.context)
                    .load(item.urls.regular)
                    .centerCrop()
                    .into(itemImageView)


            }
        }
    }
}


}