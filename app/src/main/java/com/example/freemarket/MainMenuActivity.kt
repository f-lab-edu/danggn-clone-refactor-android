package com.example.freemarket

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freemarket.databinding.ActivityMainMenuBinding
import com.example.freemarket.fragment.ChattingFragment
import com.example.freemarket.fragment.DongNaeFragment
import com.example.freemarket.fragment.HomeFragment
import com.example.freemarket.fragment.SettingFragment
import com.example.freemarket.myProduct.ProductAddActivity


/**
 * 1.setBottomNavigationView() 메소드 1줄씩 무엇인지 주석 적기
 * */

class MainMenuActivity : AppCompatActivity() {
    private val binding: ActivityMainMenuBinding by lazy {
        ActivityMainMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.nv_home
        }


        binding.btMainMenuPlus.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this,
                ProductAddActivity::class.java
            )
            startActivity(intent)
        })
    }

    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nv_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, HomeFragment()).commit()
                    true
                }

                R.id.nv_dong_nae_life -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, DongNaeFragment()).commit()
                    true
                }

                R.id.nv_chatting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ChattingFragment()).commit()
                    true
                }

                R.id.nv_setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SettingFragment()).commit()
                    true
                }

                else -> false
            }
        }
    }
}