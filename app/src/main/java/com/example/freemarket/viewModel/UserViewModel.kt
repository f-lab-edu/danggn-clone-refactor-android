package com.example.freemarket.viewModel



import com.example.freemarket.SplashActivity
import com.example.freemarket.dto.ProfileDto
import com.example.freemarket.repository.UserDB
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class UserViewModel{
    fun getUserData(splashActivity: SplashActivity, localPhoneData: String) {
        val userDb = UserDB()
        val checking = userDb.checkingUser()
        val dto = ProfileDto()

        checking.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                dto.name = value
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}