package com.example.testcentral.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testcentral.R
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.post { setContentView(R.layout.activity_splash) }
        Handler().postDelayed({
            startActivity(
                Intent(this@SplashActivity,MainActivity::class.java)
            )
        },1000)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}