package com.example.freemarket

import android.app.Dialog
import android.content.Context
import android.widget.Button
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.myProduct.ProductSelectUpdateActivity

class UpdateDeleteDialog {
    fun showDialog(
        context: Context,
        key: String,
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_product_update_delete_conform)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val btUpdate = dialog.findViewById<Button>(R.id.bt_home_product_update)
        btUpdate.setOnClickListener {
            dialog.dismiss()
        }

        val btDelete = dialog.findViewById<Button>(R.id.bt_home_product_delete)
        btDelete.setOnClickListener {
            val dao = ProductDao()
            dao.delete(key)
            dialog.dismiss()
            (context as ProductSelectUpdateActivity).finish()
        }

        val btCancel = dialog.findViewById<Button>(R.id.bt_home_product_cancel)
        btCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}
