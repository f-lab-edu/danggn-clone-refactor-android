//package com.example.test
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.freemarket.R
//import com.google.firebase.auth.FirebaseAuth
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
//    private lateinit var signOutBtn: Button
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
//
//        auth = FirebaseAuth.getInstance()
//        signOutBtn = findViewById(R.id.signOutBtn)
//
//        signOutBtn.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, PhoneActivity::class.java))
//        }
//    }
//}