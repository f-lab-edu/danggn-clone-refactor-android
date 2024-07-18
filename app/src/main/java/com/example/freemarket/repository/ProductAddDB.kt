package com.example.freemarket.repository

import android.annotation.SuppressLint


//밑에 두개의 차이점은 무엇일까?
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.freemarket.ProgressDialog
import com.example.freemarket.dao.ProductDao
import com.example.freemarket.dto.ProductDto
import com.example.freemarket.myProduct.ProductAddActivity
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import java.util.Date

//import com.google.firebase.database.core.Context

class ProductAddDB {
    fun productAdding(
        @SuppressLint("RestrictedApi") context: Context,
        activity: ProductAddActivity,
        productSubject: String,
        productPrice: String,
        productLocation: String,
        productContent: String,
        productImage: ArrayList<Uri>,
        productCategory: String,
    ) {
        val storageRef: StorageReference
        storageRef = FirebaseStorage.getInstance().getReference("product_image")

        productImage.let { it ->
            storageRef.putFile(it.get(0))
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            val imgUrl = url.toString()
                            val dao = ProductDao()
                            val product =
                                ProductDto(
                                    productSubject,
                                    productPrice,
                                    productLocation,
                                    productContent,
                                    imgUrl,
                                    productCategory,
                                    ""
                                )
                            dao.add(product).addOnSuccessListener {
                                Toast.makeText(context, "등록 성공", Toast.LENGTH_SHORT).show()
                                activity.finish()

                            }.addOnFailureListener {
                                Toast.makeText(context, "등록 실패: ${it.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                }
            for (i in 0 until it.count()) {
                imageUpload(it.get(i), i)
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun imageUpload(uri: Uri, count: Int) {
        // storage 인스턴스 생성
        val storage = Firebase.storage
        // storage 참조
        val storageRef = storage.getReference("product_image")
        // storage에 저장할 파일명 선언
        val fileName = SimpleDateFormat("yyyyMMddHHmmss_${count}").format(Date())

        val mountainsRef = storageRef.child("${fileName}.png")
        val uploadTask = mountainsRef.putFile(uri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
        }.addOnFailureListener {
            // 파일 업로드 실패
        }
    }
}