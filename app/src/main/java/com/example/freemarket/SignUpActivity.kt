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


import com.example.freemarket.databinding.ActivitySignUpBinding
import com.example.freemarket.repository.SignUserDB

import com.bumptech.glide.request.RequestOptions
import com.example.freemarket.dto.ProfileDto
import com.example.freemarket.fragment.HomeFragment
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
        //

        val profileImage =
            findViewById<ImageView>(R.id.iv_sign_up_activity_profile_image)

        setContentView(R.layout.activity_sign_up)

        //이미지 선택 - 갤러리 , 촬영 , 기본이미지

        val selectImage =
            findViewById<ImageButton>(R.id.imb_sign_up_activity_select_image)

        val profileNick =
            findViewById<EditText>(R.id.et_sign_up_activity_profile_nick)

        val finishButton =
            findViewById<Button>(R.id.bt_sign_up_activity_finish)


        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            profileImage.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }
    }
}