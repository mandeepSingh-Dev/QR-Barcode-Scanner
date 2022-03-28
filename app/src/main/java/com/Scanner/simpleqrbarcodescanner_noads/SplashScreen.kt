package com.Scanner.simpleqrbarcodescanner_noads

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import com.Scanner.simpleqrbarcodescanner_noads.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.splashCardview.animation = AnimationUtils.loadAnimation(this,R.anim.bounce)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1100)
             withContext(Dispatchers.Main){
            startActivity(Intent(this@SplashScreen, MainActivity::class.java),ActivityOptions.makeSceneTransitionAnimation(this@SplashScreen).toBundle())
                   finish()
             }
        }
    }
}