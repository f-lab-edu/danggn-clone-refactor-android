package com.example.freemarket.repository



import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginUserChekingDB {
    fun checkingUser(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        val checkUser = database.getReference("user")
        return checkUser
    }
}
