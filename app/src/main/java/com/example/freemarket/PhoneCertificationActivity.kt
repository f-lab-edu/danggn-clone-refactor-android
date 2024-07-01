package com.example.freemarket

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale
import java.util.Random

class PhoneCertificationActivity : AppCompatActivity() {

    var mCountDownTimer: CountDownTimer? = null

    //1000 == 1초
    var mTimeLeftInMillis: Long = 300000


    val SMS_SEND_PERMISSON = 1
    @SuppressLint("MissingInflatedId", "CommitPrefEdits")
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


        val preferences: SharedPreferences = getSharedPreferences("휴대폰인증번호", MODE_PRIVATE)




        val permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (permissonCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
                Toast.makeText(this, "권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.SEND_SMS),
                SMS_SEND_PERMISSON
            )
        }





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

            var check_nubmer = numberGen(4, 1)
            sendSMS(etPhoneCertificationPhoneNumber.text.toString(), "" + check_nubmer)


            val editor = preferences.edit()
            editor.putString("인증번호", check_nubmer)
            editor.apply()
        })



        //인증번호 확인
        btPhoneCertificationNumberOk.setOnClickListener(View.OnClickListener {
            if (etPhoneCertificationNumber.text.length == 0) {
                Toast.makeText(
                    this@PhoneCertificationActivity,
                    "인증번호를 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (etPhoneCertificationNumber.text.length < 4 || etPhoneCertificationNumber.text.length > 4) {
                Toast.makeText(
                    this@PhoneCertificationActivity,
                    "4자리를 입력해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }else if(!preferences.getString("인증번호", "").equals(etPhoneCertificationNumber.text.toString())) {
                Toast.makeText(
                    this@PhoneCertificationActivity,
                    "인증번호를 확인해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if(preferences.getString("인증번호", "").equals(etPhoneCertificationNumber.text.toString())) {
                mCountDownTimer?.cancel()

                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)


                val editor = preferences.edit()
                editor.remove(etPhoneCertificationNumber.text.toString())

                // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해
                // 이동한 다음 사용안함으로 finish 처리
                finish()
            }
        })

        //인증번호 다시 받기
        btPhoneCertificationGetReNumber.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@PhoneCertificationActivity, "인증번호를 전송했습니다", Toast.LENGTH_SHORT)
                .show()
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
                updateCountDownText()
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
        mTimeLeftInMillis = 300000
        startTimer()
    }

    private fun updateCountDownText() {
        val tvPhoneCertificationCountdown =
            findViewById<TextView>(R.id.tv_phone_certification_countdown)
        val minutes: Int = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds: Int = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted =
            String.format(Locale.getDefault(), "(%02d:%02d)", minutes, seconds)

        //타이머 값을 보여주게 한다
        tvPhoneCertificationCountdown.setText(timeLeftFormatted)
    }



    //인증번호를 랜덤으로 만드는 클래스
    //len-생성할 번호 난수
    //dupcd-중복 허용 여부 1-중복허용 2-중복제거
    fun numberGen(len: Int, dupCd: Int): String {
        val random = Random()
        var numStr = ""
        var i = 0
        while (i < len) {
            //0~9까지 난수 생성
            val ran = random.nextInt(10).toString()

            //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
            if (dupCd == 1) {
                //중복된 값이 없으면 numStr에 적용
                numStr += ran
            } else if (dupCd == 2) {
                if (!numStr.contains(ran)) {
                    numStr += ran
                } else {
                    i -= 1
                }
            }
            i++
        }
        return numStr
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        var sms: SmsManager? = null
        sms = SmsManager.getDefault()

        if (sms != null) {
            sms.sendTextMessage(phoneNumber, null, message, null, null)
        }
    }
}