package com.crowcel.myapplication.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.crowcel.myapplication.R
import com.crowcel.myapplication.auth.SignInActivity


class OnboardingOneActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        val btnHome = findViewById<Button>(R.id.btn_home)
        val btnSignup = findViewById<Button>(R.id.btn_signin)

        btnHome.setOnClickListener {
            val intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }
        btnSignup.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }


}