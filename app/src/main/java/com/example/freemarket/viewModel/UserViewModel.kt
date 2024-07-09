package com.example.freemarket.viewModel


import android.content.Intent
import android.util.Log
import com.example.freemarket.MainActivity
import com.example.freemarket.MainMenuActivity
import com.example.freemarket.SignUpActivity
import com.example.freemarket.SplashActivity
import com.example.freemarket.dto.ProfileDto
import com.example.freemarket.repository.LoginUserChekingDB
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class UserViewModel {
    fun getUserData(context: SplashActivity, localPhone: String) {
        val loginUserChekingDb = LoginUserChekingDB()
        val checking = loginUserChekingDb.checkingUser()


        checking.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //왜 for문으로 돌려야 할까?`
                for (ds in dataSnapshot.getChildren()) {
                    val dbPhone = ds.child("phone").getValue(String::class.java)
                    Log.e("localPhone", localPhone)
                    Log.e("dbPhone", dbPhone.toString())
                    if (localPhone == dbPhone.toString()) {

                        val intent = Intent(context, MainMenuActivity::class.java)
                        context.startActivity(intent)
                        context.finish()
                    } else {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        context.finish()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}