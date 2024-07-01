package com.example.test

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freemarket.R

class Camera : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 101
    private val CAMERA_PERMISSION_CODE = 102

    private lateinit var imageView: ImageView
    private lateinit var openCameraButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        // XML 레이아웃에서 ImageView 및 Button 참조
        imageView = findViewById(R.id.imageView)
        openCameraButton = findViewById(R.id.openCameraButton)

        // 카메라 버튼 클릭 이벤트 처리
        openCameraButton.setOnClickListener {
            // 카메라 권한 확인 및 요청
            checkCameraPermission()
        }
    }


    // 카메라 권한 확인 및 요청
    private fun checkCameraPermission() {
        // 카메라 권한이 부여되었는지 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없다면 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            // 권한이 이미 허용되었다면 사진 촬영 진행
            dispatchTakePictureIntent()
        }
    }

    // 카메라로 사진 찍기
    private fun dispatchTakePictureIntent() {
        // 카메라 앱을 실행하기 위한 Intent 생성
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용되면 사진 촬영 진행
                dispatchTakePictureIntent()
            } else {
                // 권한이 거부되었을 때 처리
                // (예: 사용자에게 권한이 필요하다는 메시지 표시)
            }
        }
    }

    // 찍은 사진 결과 표시
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // 사진 찍기 완료 후 결과 이미지를 ImageView에 설정
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }
}