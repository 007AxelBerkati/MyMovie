package com.crowcel.myapplication.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.crowcel.myapplication.R
import com.crowcel.myapplication.auth.signin.SignInActivity

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        val btnHome = findViewById<Button>(R.id.btn_home)

        btnHome.setOnClickListener({
            finishAffinity()
            startActivity(Intent(this@OnboardingThreeActivity, SignInActivity::class.java))
            finish()
        })



    }
}