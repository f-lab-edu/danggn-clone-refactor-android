package com.example.freemarket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
<<<<<<< Updated upstream:app/src/main/java/com/example/freemarket/SignUpActivity.kt
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {
=======
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.freemarket.databinding.ActivitySignUpBinding
import com.example.freemarket.repository.LocalDB
import com.example.freemarket.repository.ProfileUpdateDB
import com.example.freemarket.repository.SignUserDB


class SignUpAndUpdateActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 101
    private val CAMERA_PERMISSION_CODE = 102
    private var uri: Uri? = null
    private val SignUser = SignUserDB()
    private val profileUpdate = ProfileUpdateDB()

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

>>>>>>> Stashed changes:app/src/main/java/com/example/freemarket/SignUpAndUpdateActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//이미지 선택 - 갤러리 , 촬영 , 기본이미지
        //

<<<<<<< Updated upstream:app/src/main/java/com/example/freemarket/SignUpActivity.kt
        val profileImage =
            findViewById<ImageView>(R.id.iv_sign_up_activity_profile_image)
=======
        val localDB = LocalDB()
        val getLocalData = localDB.getLocalData(this)!!
        val existingKey = getLocalData.getString("key", "")!!
        val existingImage = getLocalData.getString("profile_image", "")
        val existingName = getLocalData.getString("name", "")

        val activity = intent.getStringExtra("activity")


        if (activity == "PhoneCertificationActivity") {
            binding.btUpdateActivity.visibility = View.INVISIBLE
        } else {
            binding.btSignUpActivityFinish.visibility = View.INVISIBLE
        }

        Glide.with(this).load(existingImage).into(binding.imvSignUpActivityProfileImage)
        binding.etSignUpActivityProfileNick.setText(existingName)


        //수정하기 버튼
        binding.btUpdateActivity.setOnClickListener {
            val updateName = binding.etSignUpActivityProfileNick.text.toString()
            uri?.let { it1 ->
                profileUpdate.profileUpdate(applicationContext,this, existingKey, updateName, it1)
            }
        }


        //회원가입 버튼
        val phone = intent.getStringExtra("phone")
        binding.btSignUpActivityFinish.setOnClickListener(View.OnClickListener {
            if (binding.etSignUpActivityProfileNick.text.length == 0) {
                Toast.makeText(
                    this@SignUpAndUpdateActivity,
                    "닉네임을 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etSignUpActivityProfileNick.text.length == 1) {
                Toast.makeText(
                    this@SignUpAndUpdateActivity,
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
                uri?.let { it1 -> SignUser.signingUser(this, name, phone!!, it1) }
                binding.laoding.visibility = View.VISIBLE
            }
        })
>>>>>>> Stashed changes:app/src/main/java/com/example/freemarket/SignUpAndUpdateActivity.kt

        val selectImage =
            findViewById<ImageButton>(R.id.imb_sign_up_activity_select_image)

<<<<<<< Updated upstream:app/src/main/java/com/example/freemarket/SignUpActivity.kt
        val profileNick =
            findViewById<EditText>(R.id.et_sign_up_activity_profile_nick)
=======
        //이미지 선택 - 갤러리 , 촬영 , 기본이미지
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imvSignUpActivityProfileImage.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }
>>>>>>> Stashed changes:app/src/main/java/com/example/freemarket/SignUpAndUpdateActivity.kt

        val finishButton =
            findViewById<Button>(R.id.bt_sign_up_activity_finish)


<<<<<<< Updated upstream:app/src/main/java/com/example/freemarket/SignUpActivity.kt
        //인증번호 확인
        finishButton.setOnClickListener(View.OnClickListener {
            if (profileNick.text.length == 0){
                Toast.makeText(
                    this@SignUpActivity,
                    "닉네임을 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }else if(profileNick.text.length ==  1){
                Toast.makeText(
                    this@SignUpActivity,
                    "2자리 이상 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }else{

            }
        })
    }
=======
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
        binding.imvSignUpActivityProfileImage.apply {
            setImageResource(R.drawable.ic_launcher_background)
            RequestOptions().circleCrop()
        }
    }
>>>>>>> Stashed changes:app/src/main/java/com/example/freemarket/SignUpAndUpdateActivity.kt
}