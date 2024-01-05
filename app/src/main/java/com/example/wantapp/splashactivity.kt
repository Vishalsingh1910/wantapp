package com.example.wantapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class splashactivity : AppCompatActivity() {

    private val splashScreenTime = 4000
    private lateinit var  imageGif : ImageView
    private lateinit var  topAnim  : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashactivity)
        imageGif = findViewById(R.id.splashgif)
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim)
        imageGif.animation = topAnim
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this@splashactivity,Authentication::class.java)
                startActivity(intent)
                finish()
            },splashScreenTime.toLong()
        )
    }

}