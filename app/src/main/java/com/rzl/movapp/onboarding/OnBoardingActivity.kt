package com.rzl.movapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rzl.movapp.databinding.ActivityOnBoardingBinding
import com.rzl.movapp.R
import com.rzl.movapp.home.HomeActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setTheme(R.style.Theme_MovApp)


        binding.btnEnterNow.setOnClickListener {
            val intent = Intent(this@OnBoardingActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}