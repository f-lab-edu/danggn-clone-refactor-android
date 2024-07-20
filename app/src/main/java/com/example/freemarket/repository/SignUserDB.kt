package com.example.freemarket.repository

import android.content.Intent
import android.net.Uri
import com.example.freemarket.MainMenuActivity
import com.example.freemarket.SignUpAndUpdateActivity
import com.example.freemarket.dto.ProfileDto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SignUserDB {
    fun signingUser(
        context: SignUpAndUpdateActivity,
        name: String,
        phone: String,
        uri: Uri,
    ) {
        val firebaseRef: DatabaseReference
        val storageRef: StorageReference
        val localDB = LocalDB()

        firebaseRef = FirebaseDatabase.getInstance().getReference("user")
        storageRef = FirebaseStorage.getInstance().getReference("profileImage")

        val contactId = firebaseRef.push().key!!
        var contacts: ProfileDto

        uri.let {
            storageRef
                .child(contactId)
                .putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!
                        .reference!!
                        .downloadUrl
                        .addOnSuccessListener { url ->
                            val imgUrl = url.toString()
                            contacts = ProfileDto(name, imgUrl, phone)
                            firebaseRef
                                .child(contactId)
                                .setValue(contacts)
                                .addOnCompleteListener {
                                    localDB.saveLocalData(context, contactId, phone, name, imgUrl)
                                    val intent = Intent(context, MainMenuActivity::class.java)
                                    context.startActivity(intent)
                                    context.finish()
                                }.addOnFailureListener { error ->
                                }
                        }
                }
        }
    }
}
