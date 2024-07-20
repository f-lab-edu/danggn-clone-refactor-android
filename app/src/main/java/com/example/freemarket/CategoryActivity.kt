package com.example.freemarket

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.databinding.ActivityCategoryBinding
import com.example.freemarket.repository.LocalDB

class CategoryActivity : AppCompatActivity() {
    private val binding: ActivityCategoryBinding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btDigital.setOnClickListener(
            View.OnClickListener {
                val localDB = LocalDB()
                val shared = localDB.getLocalData(this)!!
                Log.d("shared 확인 ", shared.toString())
                shared.edit().putString("category", "디지털기기")
                shared.edit().apply() // 변경사항 저장 완료
                finish()
            },
        )
    }
}
