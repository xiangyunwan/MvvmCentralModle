package com.example.testcentral.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.example.testcentral.ui.MainActivity

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@TestActivity,
                    MainActivity::class.java
                )
            )
        }, 1000)
    }
}