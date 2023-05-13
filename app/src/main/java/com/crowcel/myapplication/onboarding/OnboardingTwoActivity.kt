package com.crowcel.myapplication.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.crowcel.myapplication.R

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        val btnHome = findViewById<Button>(R.id.btn_home)


        btnHome.setOnClickListener({
            startActivity(Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java))
            finish()
        })




    }
}