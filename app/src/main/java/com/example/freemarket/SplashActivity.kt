package com.example.freemarket

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.dto.ProfileDto

import com.example.freemarket.repository.LocalDB

import com.example.freemarket.viewModel.UserViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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
        notUser()
    }

    fun checkingUser() {
        //쓰기
//        val preferences: SharedPreferences = getSharedPreferences("회원", MODE_PRIVATE)
//        val plantList = ArrayList<ProfileDto>()
//
//        if (preferences.getString("유저", "").equals(value)) {
//            User()
//        } else {
//            notUser()
//        }
//        notUser()

    }

    //스플래쉬 로딩 핸들러
    fun notUser() {
        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000) // 시간 1초 이후 실행
    }

    fun User() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000) // 시간 1초 이후 실행
    }

    override fun onStart() {
        val model = UserViewModel()
        super.onStart()
    }


}