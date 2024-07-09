package com.example.freemarket

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.RequestOptions
import com.example.freemarket.databinding.ActivityProductAddBinding
import com.example.freemarket.databinding.ActivitySignUpBinding
import com.example.freemarket.dto.ProfileDto
import com.example.freemarket.fragment.HomeFragment
import com.example.freemarket.repository.SignUserDB
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SignUpActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 101
    private val CAMERA_PERMISSION_CODE = 102
    private var uri: Uri? = null
    private val SignUser = SignUserDB()
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.laoding.visibility = View.INVISIBLE

//이미지 선택 - 갤러리 , 촬영 , 기본이미지



        val phone = intent.getStringExtra("phone")


        //이미지 선택 - 갤러리 , 촬영 , 기본이미지
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ivSignUpActivityProfileImage.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }


        binding.imbSignUpActivitySelectImage.setOnClickListener(View.OnClickListener {
            val dialog = android.app.Dialog(this)
            dialog.setContentView(R.layout.dialog_sign_up_image)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setCancelable(true)
            dialog.show()


            //직접 촬영
            val camera = dialog.findViewById<Button>(R.id.bt_sign_up_dialog_camera)
            camera.setOnClickListener() {
                checkCameraPermission()
                dialog.dismiss()
            }

            //갤러리
            val gallery = dialog.findViewById<Button>(R.id.bt_sign_up_dialog_gallery)
            gallery.setOnClickListener() {
                dialog.dismiss()
                pickImage.launch("image/*")
            }

            //기본 이미지
            val base = dialog.findViewById<Button>(R.id.bt_sign_up_dialog_base_image)
            base.setOnClickListener() {
                defaultImage()
                dialog.dismiss()
            }
        })


        //인증번호 확인
        binding.btSignUpActivityFinish.setOnClickListener(View.OnClickListener {
            if (binding.etSignUpActivityProfileNick.text.length == 0) {
                Toast.makeText(
                    this@SignUpActivity,
                    "닉네임을 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etSignUpActivityProfileNick.text.length == 1) {
                Toast.makeText(
                    this@SignUpActivity,
                    "2자리 이상 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                val name = binding.etSignUpActivityProfileNick.text.toString()

                //? - null일 가능성 있는 변수임을 알려줌
                //let - 만약에 not null 일 경우 사용하는 함수
                //it - it receiver 객체를 받을수 있게 하는 변수 밑에서 it1은 uri변수를 말함
                //-> - 함수를 뜻하는 의미

                uri?.let { it1 -> SignUser.signingUser(this,name,phone!!, it1) }
                binding.laoding.visibility = View.VISIBLE

            }
        })
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


    //기본 이미지 설정
    @SuppressLint("CheckResult")
    private fun defaultImage() {
        val profileImage =
            findViewById<ImageView>(R.id.iv_sign_up_activity_profile_image)
        profileImage.apply {
            setImageResource(R.drawable.ic_launcher_background)
            RequestOptions().circleCrop()
        }
    }
}