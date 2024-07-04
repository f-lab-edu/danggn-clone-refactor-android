package com.example.freemarket.repository

<<<<<<< Updated upstream:app/src/main/java/com/example/freemarket/repository/UserDB.kt
class UserDB {
}
=======
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginUserChekingDB {
    fun checkingUser(): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        val checkUser = database.getReference("user")
        return checkUser
    }
}
>>>>>>> Stashed changes:app/src/main/java/com/example/freemarket/repository/LoginUserChekingDB.kt
