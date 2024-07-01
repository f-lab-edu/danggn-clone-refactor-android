package com.example.freemarket.viewModel

import com.example.freemarket.dto.ProfileDto
import com.example.freemarket.repository.UserDB
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class UserViewModel{
    fun getUserData() {
        val userDb = UserDB()
        val checking = userDb.checkingUser()
        val dto = ProfileDto()

        checking.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                println(value+ "여기까지 들어오나?22222")
                dto.name = value
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}