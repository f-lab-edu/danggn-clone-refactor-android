package com.example.freemarket.myProduct

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freemarket.R


class ImageAdapter(val context: ProductAddActivity, val items: ArrayList<Uri>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener
    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: Uri) {
            val imageArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val delete = itemView.findViewById<Button>(R.id.btnDelete)

            delete.setOnClickListener {
                val position = adapterPosition
                itemClickListener.onItemClick(position)
            }

            Glide.with(context).load(item).into(imageArea)
        }
    }
}