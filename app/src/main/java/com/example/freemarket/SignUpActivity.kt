package com.example.freemarket


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.freemarket.databinding.ActivitySignUpBinding
import com.example.freemarket.repository.SignUserDB


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

        val selectImage =
            findViewById<ImageButton>(R.id.imb_sign_up_activity_select_image)

        val phone = intent.getStringExtra("phone")


        //이미지 선택 - 갤러리 , 촬영 , 기본이미지
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imvSignUpActivityProfileImage.setImageURI(it)
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

//                uri?.let { it1 -> SignUser.signingUser(this, name, phone!!, it1) }
//                binding.laoding.visibility = View.VISIBLE

                val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
                    if (it != null) {
                        uri = it
                    }
                }
            }
        })
    }
}


