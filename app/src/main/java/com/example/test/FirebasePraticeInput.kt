// package com.example.test
//
// import android.annotation.SuppressLint
// import android.content.Intent
// import android.os.Bundle
// import android.view.View
// import android.widget.Button
// import android.widget.EditText
// import android.widget.Toast
// import androidx.appcompat.app.AppCompatActivity
// import com.example.freemarket.R
// import com.google.android.gms.tasks.OnCompleteListener
// import com.google.firebase.Firebase
// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
// import com.google.firebase.auth.PhoneAuthCredential
// import com.google.firebase.auth.PhoneAuthProvider
//
// import com.google.firebase.database.database
//
// class FirebasePraticeInput : AppCompatActivity() {
//    lateinit var auth: FirebaseAuth
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_firebase_pratice_input)
//
//        val et1 =
//            findViewById<EditText>(R.id.et2_firebasephone)
//        val bt =
//            findViewById<Button>(R.id.bt2_firebasephone)
//
//        auth = FirebaseAuth.getInstance()
//
//        val storedVerificationId = intent.getStringExtra("storedVerificationId")
//
//        bt.setOnClickListener {
//            var et = et1.text.toString()
//            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
//                storedVerificationId.toString(), et
//            )
//            signInWithPhoneAuthCredential(credential)
//        }
//    }
//
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    println("연결이 되었습니다")
//                } else {
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//    }
//
//
// //    fun addDb(et12:String , et22:String){
// ////해시맵 선언
// //        val result: HashMap<String, String> = HashMap()
// //        val et1 =
// //            findViewById<EditText>(R.id.et_1firebase)
// //        val et2 =
// //            findViewById<EditText>(R.id.et_2firebase)
// //        //요소 추가
// //        result.put("et1", et12)
// //        result.put("et2", et22)
// //
// //        val database = Firebase.database
// //        val myRef = database.getReference("message")
// //
// //        val key = myRef.push().key
// //        key?.let { result.put("key", it) }
// //        if (key != null) {
// //            myRef.child(key).setValue(result).addOnCompleteListener(OnCompleteListener {
// //                et1.text.clear()
// //                et2.text.clear()
// //            })
// //        }
// //
// //    }
// }
