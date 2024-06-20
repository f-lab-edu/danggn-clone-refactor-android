package com.example.freemarket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//이미지 선택 - 갤러리 , 촬영 , 기본이미지
        //

        val profileImage =
            findViewById<ImageView>(R.id.iv_sign_up_activity_profile_image)

        val selectImage =
            findViewById<ImageButton>(R.id.imb_sign_up_activity_select_image)

        val profileNick =
            findViewById<EditText>(R.id.et_sign_up_activity_profile_nick)

        val finishButton =
            findViewById<Button>(R.id.bt_sign_up_activity_finish)


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
}