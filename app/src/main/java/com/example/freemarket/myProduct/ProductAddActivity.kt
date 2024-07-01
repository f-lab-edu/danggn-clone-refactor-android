package com.example.freemarket.myProduct

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.R
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.databinding.ActivityMainMenuBinding
import com.example.freemarket.databinding.ActivityProductAddBinding
import com.example.freemarket.dto.ProductDto

class ProductAddActivity : AppCompatActivity() {
    private val binding: ActivityProductAddBinding by lazy {
        ActivityProductAddBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //데이터베이스 클래스 객체 생성
        val dao = ProductDao()

        //사용자 등록 버튼 이벤트
        binding.btProductAddComplete.setOnClickListener {

            val productSubject = binding.etProductAddSubject.text.toString() //이름
            val productPrice = binding.etProductAddPrice.text.toString() //나이
            val productLocation = binding.etProductAddLocation.text.toString() //나이
            val productContent = binding.etProductAddContent.text.toString() //나이

            //데이터 셋팅
            val product = ProductDto("", productSubject, productPrice,productLocation,productContent)

            dao.add(product).addOnSuccessListener {
                Toast.makeText(this, "등록 성공", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, "등록 실패: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}