package com.example.freemarket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val alreadyUserTextview = findViewById<TextView>(R.id.tv_already_user)
        val notUserButton = findViewById<Button>(R.id.bt_not_user)

        notUserButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this,
                PhoneCertificationActivity::class.java
            )
            startActivity(intent)
        })


        alreadyUserTextview.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this,
                PhoneCertificationActivity::class.java
            )
            startActivity(intent)
        })
    }
}