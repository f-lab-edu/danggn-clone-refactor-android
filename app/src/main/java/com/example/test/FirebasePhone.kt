package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class FirebasePhone : AppCompatActivity() {
    val auth = Firebase.auth
    var verificationId = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_phone)

        val et_firebasephone =
            findViewById<EditText>(R.id.et_firebasephone)

        val bt_firebasephone =
            findViewById<Button>(R.id.bt_firebasephone)

        val et2_firebasephone =
            findViewById<EditText>(R.id.et2_firebasephone)

        val bt2_firebasephone =
            findViewById<Button>(R.id.bt2_firebasephone)

        bt_firebasephone.setOnClickListener(
            View.OnClickListener {
                val callbacks =
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            signInWithPhoneAuthCredential(credential)
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken,
                        ) {
                            this@FirebasePhone.verificationId = verificationId
                            println(verificationId + "    verificationId")
                        }
                    }

                val optionsCompat =
                    PhoneAuthOptions
                        .newBuilder(auth)
                        .setPhoneNumber("+821012345678")
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build()
                PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
                auth.setLanguageCode("kr")
            },
        )

        bt2_firebasephone.setOnClickListener(
            View.OnClickListener {
                val credential = PhoneAuthProvider.getCredential(verificationId, "123444")
                signInWithPhoneAuthCredential(credential)
            },
        )
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth
            .signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    println("연결이 되었습니다")
                } else {
                    // 인증실패
                    println("연결이 안되었습니다")
                }
            }
    }
}
