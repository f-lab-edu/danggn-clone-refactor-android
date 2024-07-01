package com.example.freemarket.myProduct

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freemarket.R
import com.example.freemarket.databinding.ActivityProductAddBinding
import com.example.freemarket.databinding.ActivityProductSelectUpdateBinding

class ProductSelectUpdateActivity : AppCompatActivity() {
    private val binding: ActivityProductSelectUpdateBinding by lazy {
        ActivityProductSelectUpdateBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //데이터 담기
        val productSubject = intent.getStringExtra("productSubject")!!
        val productPrice = intent.getStringExtra("productPrice")!!
        val productLocation = intent.getStringExtra("productLocation")!!
        val productContent = intent.getStringExtra("productContent")!!


        //데이터 보여주기
        binding.tvProductSelectUpdateSubject.setText(productSubject)
        binding.tvProductSelectUpdatePrice.setText(productPrice)
        binding.tvProductSelectUpdateLocation.setText(productLocation)
        binding.tvProductSelectUpdateContent.setText(productContent)

    }
}