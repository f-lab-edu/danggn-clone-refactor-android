package com.example.freemarket.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.freemarket.SignUpAndUpdateActivity
import com.example.freemarket.dao.ProfileDao
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileUpdateDB {
    fun profileUpdate(
        context: Context,
        activity: SignUpAndUpdateActivity,
        existingKey: String,
        name: String,
        uri: Uri,
    ) {
        val storageRef: StorageReference
        val localDB = LocalDB()
        storageRef = FirebaseStorage.getInstance().getReference("profileImage")

        uri.let {
            storageRef
                .child(existingKey)
                .putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!
                        .reference!!
                        .downloadUrl
                        .addOnSuccessListener { url ->

                            val imgUrl = url.toString()
                            localDB.updateLocalData(activity, name, imgUrl)

                            val dao = ProfileDao()
                            val hasMap: HashMap<String, Any> = HashMap()
                            hasMap["name"] = name
                            hasMap["imgUri"] = imgUrl

                            // 사용자 업데이트 이벤트
                            dao
                                .update(existingKey, hasMap)
                                .addOnSuccessListener {
                                    Toast
                                        .makeText(
                                            context,
                                            "수정 되었습니다",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                }.addOnFailureListener {
                                    Toast
                                        .makeText(
                                            context,
                                            "수정 실패: ${it.message}",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                }
                        }
                }
        }
    }
}
