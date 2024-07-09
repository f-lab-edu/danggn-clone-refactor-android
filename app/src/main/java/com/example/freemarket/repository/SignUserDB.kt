package com.example.freemarket.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import com.example.freemarket.MainMenuActivity
import com.example.freemarket.SignUpActivity
import com.example.freemarket.dto.ProfileDto
import com.example.freemarket.fragment.HomeFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SignUserDB {
    fun signingUser(context: SignUpActivity, name: String, uri: Uri) {
        val firebaseRef: DatabaseReference
        val storageRef: StorageReference


        firebaseRef = FirebaseDatabase.getInstance().getReference("user")
        storageRef = FirebaseStorage.getInstance().getReference("profileImage")

        val contactId = firebaseRef.push().key!!
        var contacts: ProfileDto

        uri.let {
            storageRef.child(contactId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            val imgUrl = url.toString()
                            contacts = ProfileDto(name, imgUrl)
                            firebaseRef.child(contactId).setValue(contacts)
                                .addOnCompleteListener {
                                    val intent = Intent(context, MainMenuActivity::class.java)
                                    context.startActivity(intent)
                                    context.finish()
                                }
                                .addOnFailureListener { error ->
                                }
                        }
                }
        }
    }
}