package com.example.freemarket

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.repository.LocalDB
import com.example.freemarket.viewModel.UserViewModel


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //로컬에 저장되어있는지 확인
        val localData = LocalDB()
        val localPhoneData = localData.loadLocalData(this)


        val userViewModel = UserViewModel()
        userViewModel.getUserData(this,localPhoneData!!)

//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 1000) // 시간 1초 이후 실행
    }
}
