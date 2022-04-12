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
import com.example.retrofitapp.databinding.ActivityMainBinding

import com.example.retrofitapp.retrofitApi


class My_Adapter:ListAdapter<item_data,My_Adapter.ItemViewHolder>(DiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): My_Adapter.ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val getItem: MutableList<item_data>
        val item = getItem(position)
        holder.bind(item)
    }






    class ItemViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: item_data){
            binding.apply {
                itemTitle.text = item.user.name
                item_address.text = item.user.location
                item_desc.text = item.user.bio

                Glide.with(item_imageView.context)
                    .load(item.urls.regular)
                    .centerCrop()
                    .into(item_imageView)


            }
        }
    }
}
// github token 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


