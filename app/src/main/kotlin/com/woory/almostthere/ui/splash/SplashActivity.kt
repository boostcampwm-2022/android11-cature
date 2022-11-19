package com.woory.almostthere.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.woory.almostthere.R
import com.woory.almostthere.databinding.ActivitySplashBinding
import com.woory.almostthere.ui.ActivityViewBindingDelegate
import com.woory.almostthere.ui.main.MainActivity
import com.woory.almostthere.util.textScaleAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by ActivityViewBindingDelegate(R.layout.activity_splash)

    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler.postDelayed({
            binding.tvNext.visibility = View.VISIBLE
            binding.tvNext.startAnimation(textScaleAnimation)

            binding.root.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}