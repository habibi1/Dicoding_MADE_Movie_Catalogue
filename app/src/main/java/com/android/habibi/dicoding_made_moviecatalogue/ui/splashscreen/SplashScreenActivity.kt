package com.android.habibi.dicoding_made_moviecatalogue.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.habibi.dicoding_made_moviecatalogue.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toMainActivity()
    }

    private fun toMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}