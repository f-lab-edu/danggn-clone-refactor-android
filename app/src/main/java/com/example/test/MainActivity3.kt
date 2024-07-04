package com.example.test

import android.R
import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.databinding.ActivityMain3Binding
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity3 : AppCompatActivity() {
    private lateinit var uri: Uri
    private val binding: ActivityMain3Binding by lazy {
        ActivityMain3Binding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //프로그래스바 숨기기

        // 이미지뷰를 눌렀을 경우
//        binding.imageArea.setOnClickListener {
//            // ACTION_PICK을 사용하여 앨범을 호출한다.
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            registerForActivityResult.launch(intent)
//        }

        // 등록하기 버튼을 눌렀을 경우
        binding.btnRegister.setOnClickListener {
            selectImage()
//            imageUpload(uri)
        }


    }



    @SuppressLint("SimpleDateFormat")
    private fun imageUpload(uri: Uri) {
        // storage 인스턴스 생성
        val storage = Firebase.storage
        // storage 참조
        val storageRef = storage.getReference("image")
        // storage에 저장할 파일명 선언
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mountainsRef = storageRef.child("${fileName}.png")

        val uploadTask = mountainsRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
            Toast.makeText(this, "사진 업로드 성공", Toast.LENGTH_SHORT).show();
        }.addOnFailureListener {
            // 파일 업로드 실패
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_SHORT).show();
        }
    }

    fun selectImage(){
        val registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        // 변수 uri에 전달 받은 이미지 uri를 넣어준다.
                        uri = result.data?.data!!
                        // 이미지를 ImageView에 표시한다.
                        binding.imageArea.setImageURI(uri)
                    }
                }
            }
    }
}
