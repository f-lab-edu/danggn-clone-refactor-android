package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirebasePratice : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_pratice)

        val main_plus_button =
            findViewById<FloatingActionButton>(R.id.main_plus_button)

        main_plus_button.setOnClickListener(
            View.OnClickListener {
            },
        )
    }
}
