package com.example.freemarket.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freemarket.R
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.dto.ProductDto
import com.example.freemarket.myProduct.ProductSelectUpdateActivity

class HomeAdapter(private val context: Context, private val productList:ArrayList<ProductDto>):
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){


    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.home_item, parent, false)

        return HomeViewHolder(view)
    }

    //데이터 설정
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val product: ProductDto = productList[position]

        holder.homeSubject.text = product.productSubject
        holder.homePrice.text = product.productPrice

        holder.itemView.setOnClickListener {

            //사용자 수정화면으로 이동
            val intent = Intent(context, ProductSelectUpdateActivity::class.java)
            intent.putExtra("key", product.productKey)
            intent.putExtra("productSubject", product.productSubject)
            intent.putExtra("productPrice", product.productPrice)
            intent.putExtra("productLocation", product.productLocation)
            intent.putExtra("productContent", product.productContent)
            context.startActivity(intent)
            (context as Activity).finish()
        }

        holder.homeUpdateDeleteDialog.setOnClickListener {
            showDialog(position)
        }
    }

    //값 갯수 리턴
    override fun getItemCount(): Int {
        return productList.size
    }

    class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val homeSubject: TextView = itemView.findViewById(R.id.tv_home_subject)
        val homePrice: TextView = itemView.findViewById(R.id.tv_home_price)
        val homeUpdateDeleteDialog: ImageView = itemView.findViewById(R.id.iv_home_product_item_dialog)
    }


    fun showDialog(position: Int) {
        val dialog = android.app.Dialog(context)
        dialog.setContentView(R.layout.dialog_product_update_delete_conform)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        //다이얼로그 취소하기
        val btUpdate = dialog.findViewById<Button>(R.id.bt_home_product_update)
        btUpdate.setOnClickListener() {
            dialog.dismiss()
        }

        val btDelete = dialog.findViewById<Button>(R.id.bt_home_product_delete)
        btDelete.setOnClickListener() {
            dialog.dismiss()
            val key = productList[position].productKey
            lateinit var dao: ProductDao
            dao.delete(key)
        }

        val btCancel = dialog.findViewById<Button>(R.id.bt_home_product_cancel)
        btCancel.setOnClickListener() {
            dialog.dismiss()
        }
    }
}