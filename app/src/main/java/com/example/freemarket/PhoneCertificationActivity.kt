package com.example.freemarket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class PhoneCertificationActivity : AppCompatActivity() {

    var mCountDownTimer: CountDownTimer? = null

    //숫자를 크게한 이유는 countdowninterval가 1000의 1초이기 때문이다
    var mTimeLeftInMillis: Long = 10000

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_certification)


        val etPhoneCertificationPhoneNumber =
            findViewById<EditText>(R.id.et_phone_certification_phone_number)
        val btPhoneCertificationGetNumber =
            findViewById<Button>(R.id.bt_phone_certification_get_number)
        val ibtPhoneCertificationBack = findViewById<ImageButton>(R.id.ibt_phone_certification_back)
        val btPhoneCertificationGetReNumber =
            findViewById<Button>(R.id.bt_phone_certification_get_renumber)
        val etPhoneCertificationNumber = findViewById<EditText>(R.id.et_phone_certification_number)
        val btPhoneCertificationNumberOk =
            findViewById<Button>(R.id.bt_phone_certification_number_ok)
        //첫화면에 Gone하는 뷰
        //이유-인증문자 받기를 클릭하게되면 활성화되는 뷰
        firstActivityGone()


        //백버튼
        ibtPhoneCertificationBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
            finish()
        })

        //인증번호 get 하는 버튼
        btPhoneCertificationGetNumber.setOnClickListener(View.OnClickListener {
            btPhoneCertificationGetNumber.setVisibility(View.GONE)
            startTimer()
            firstActivityVisible()
        })

        //인증번호 확인
        btPhoneCertificationNumberOk.setOnClickListener(View.OnClickListener {
            if (etPhoneCertificationNumber.text.length == 0){
                Toast.makeText(
                    this@PhoneCertificationActivity,
                    "인증번호를 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }else if(etPhoneCertificationNumber.text.length < 4 || etPhoneCertificationNumber.text.length > 4){
                Toast.makeText(
                    this@PhoneCertificationActivity,
                    "4자리를 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }else{
                mCountDownTimer?.cancel()

                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)

                // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해
                // 이동한 다음 사용안함으로 finish 처리
                finish()
            }
        })

        //인증번호 다시 받기
        btPhoneCertificationGetReNumber.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@PhoneCertificationActivity, "인증번호를 전송했습니다", Toast.LENGTH_SHORT)
                .show()
            updateCountDownText(false)
            resetTimer()
        })


        etPhoneCertificationPhoneNumber.addTextChangedListener(object : TextWatcher {
            //beforeTextChanged-입력하여 변화가 생기기 전에 처리하는 부분
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            //onTextChanged-입력난에 변화가 있을시
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}


            override fun afterTextChanged(editable: Editable) {

                if (editable.length > 7) {
                    //글자수가 8개부터 버튼이 활성화가 되고 텍스트 컬러가 보이게 된다
                } else {

                }
            }
        })



    }

    fun firstActivityGone() {
        val btPhoneCertificationGetReNumber =
            findViewById<Button>(R.id.bt_phone_certification_get_renumber)
        val tvPhoneCertificationCountdown =
            findViewById<TextView>(R.id.tv_phone_certification_countdown)
        val etPhoneCertificationNumber = findViewById<EditText>(R.id.et_phone_certification_number)
        val tvPhoneCertificationNumberError =
            findViewById<TextView>(R.id.tv_phone_certification_number_error)
        val btPhoneCertificationNumberOk =
            findViewById<Button>(R.id.bt_phone_certification_number_ok)

        btPhoneCertificationGetReNumber.setVisibility(View.GONE)
        etPhoneCertificationNumber.setVisibility(View.GONE)
        tvPhoneCertificationNumberError.setVisibility(View.GONE)
        btPhoneCertificationNumberOk.setVisibility(View.GONE)
        tvPhoneCertificationCountdown.setVisibility(View.GONE)
    }


    fun firstActivityVisible() {
        val btPhoneCertificationGetReNumber =
            findViewById<Button>(R.id.bt_phone_certification_get_renumber)
        val tvPhoneCertificationCountdown =
            findViewById<TextView>(R.id.tv_phone_certification_countdown)
        val etPhoneCertificationNumber = findViewById<EditText>(R.id.et_phone_certification_number)
        val btPhoneCertificationNumberOk =
            findViewById<Button>(R.id.bt_phone_certification_number_ok)

        btPhoneCertificationGetReNumber.setVisibility(View.VISIBLE)
        etPhoneCertificationNumber.setVisibility(View.VISIBLE)
        btPhoneCertificationNumberOk.setVisibility(View.VISIBLE)
        tvPhoneCertificationCountdown.setVisibility(View.VISIBLE)
    }


    //기존에 시작하는 타이머 어떻게 종료할까
    private fun startTimer() {
        //CountDownTimer 객체를 만드는 법
        //countdowninterval-콜백을 받기까지의 시간 간격
        //첫번째 인자의 값이 5000이면 5초이고,두번째 인자가 1000이면 1초이다
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText(true)
            }

            override fun onFinish() {
                Toast.makeText(
                    this@PhoneCertificationActivity,
                    "시간이 초과 되었습니다",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }.start()
    }

    //리셋을 할때 mTimeLeftInMillis을 온크리에이트에서 설정한 값을 재설정을 해준다
    private fun resetTimer() {
        //카운터 재갱신
        mCountDownTimer?.cancel()
        mTimeLeftInMillis = 10000
        startTimer()
    }

    private fun updateCountDownText(start: Boolean) {
        if (start) {
            val tvPhoneCertificationCountdown =
                findViewById<TextView>(R.id.tv_phone_certification_countdown)
            val minutes: Int = (mTimeLeftInMillis / 1000).toInt() / 60
            val seconds: Int = (mTimeLeftInMillis / 1000).toInt() % 60
            val timeLeftFormatted =
                String.format(Locale.getDefault(), "(%02d:%02d)", minutes, seconds)

            //타이머 값을 보여주게 한다
            tvPhoneCertificationCountdown.setText(timeLeftFormatted)
        }
        else{
            return
        }
    }
}