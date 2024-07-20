package com.example.freemarket.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freemarket.R
import com.example.freemarket.dto.ProductDto
import com.example.freemarket.myProduct.ProductSelectUpdateActivity

class HomeAdapter(
    private val context: Context,
    private val productList: ArrayList<ProductDto>,
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    // 화면 설정
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeViewHolder {
        val view =
            LayoutInflater
                .from(context)
                .inflate(R.layout.home_item, parent, false)

        return HomeViewHolder(view)
    }

    // 데이터 설정
    override fun onBindViewHolder(
        holder: HomeViewHolder,
        position: Int,
    ) {
        val product: ProductDto = productList[position]
        holder.homeSubject.text = product.productSubject
        Glide.with(context).load(product.productImage).into(holder.homeMianImage)
        holder.homePrice.text = context.getResources().getString(R.string.home_fragment_item_price).format(product.productPrice.toInt())

        holder.itemView.setOnClickListener {
            // 사용자 수정화면으로 이동
            val intent = Intent(context, ProductSelectUpdateActivity::class.java)
            intent.putExtra("productSubject", product.productSubject)
            intent.putExtra("productPrice", product.productPrice)
            intent.putExtra("productLocation", product.productLocation)
            intent.putExtra("productContent", product.productContent)
            intent.putExtra("productKey", product.productKey)
            context.startActivity(intent)
        }
    }

    // 값 갯수 리턴
    override fun getItemCount(): Int = productList.size

    class HomeViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val homeSubject: TextView = itemView.findViewById(R.id.tv_home_subject)
        val homePrice: TextView = itemView.findViewById(R.id.tv_home_price)

        val homeMianImage: ImageView = itemView.findViewById(R.id.imv_home_main_image)
    }
}
