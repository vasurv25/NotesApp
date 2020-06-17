package com.example.notesapp.splash

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import androidx.core.app.ActivityOptionsCompat
import com.example.notesapp.GetStartedActivity
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_get_started.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1500 //3 seconds

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, GetStartedActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "app_logo_transition")
            startActivity(intent, options.toBundle())

        }
    }

    override fun init() {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
    }
}
