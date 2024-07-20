package com.example.freemarket.myProduct

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.UpdateDeleteDialog
import com.example.freemarket.databinding.ActivityProductSelectUpdateBinding

class ProductSelectUpdateActivity : AppCompatActivity() {
    private val binding: ActivityProductSelectUpdateBinding by lazy {
        ActivityProductSelectUpdateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터 담기
        val productSubject = intent.getStringExtra("productSubject")!!
        val productPrice = intent.getStringExtra("productPrice")!!
        val productLocation = intent.getStringExtra("productLocation")!!
        val productContent = intent.getStringExtra("productContent")
        val productKey = intent.getStringExtra("productKey")!!

        // 데이터 보여주기
        binding.tvProductSelectUpdateSubject.setText(productSubject)
        binding.tvProductSelectUpdatePrice.setText(productPrice)
        binding.tvProductSelectUpdateLocation.setText(productLocation)
        binding.tvProductSelectUpdateContent.setText(productContent)
        binding.imvHomeProductItemDialog.setOnClickListener(
            View.OnClickListener {
                updateDeleteOpenDialog(productKey)
            },
        )
    }

    fun updateDeleteOpenDialog(key: String) {
        val dialog = UpdateDeleteDialog()
        dialog.showDialog(this, key)
    }
}
