package com.example.notesapp

import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.notesapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_get_started.*
import android.view.View
import android.animation.ValueAnimator
import androidx.databinding.DataBindingUtil
import com.example.notesapp.databinding.ActivityGetStartedBinding
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.PorterDuff
import android.graphics.Color.parseColor
import android.content.Intent
import android.util.Log
import android.view.View.*
import android.view.ViewAnimationUtils


class GetStartedActivity : BaseActivity() {

    private var mDelayHandler: Handler? = null
    private lateinit var mGetStartedBinding: ActivityGetStartedBinding

    private val mRunnableButton: Runnable = Runnable {
        val animSlideDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_down)
        fl_get_started.startAnimation(animSlideDown)
        fl_get_started.visibility = VISIBLE
//        val colorAnim = ObjectAnimator.ofInt(button2, "textColor", Color.WHITE, Color.TRANSPARENT)
//        colorAnim.run {
//            duration = 1000
//            setEvaluator(ArgbEvaluator())
//            repeatCount = ValueAnimator.INFINITE
//            repeatMode = ValueAnimator.REVERSE
//            start()
//        }
    }

    private val mRunnableText: Runnable = Runnable {
        val animSlideDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_down)
        tv_get_started.startAnimation(animSlideDown)
        tv_get_started.visibility = VISIBLE
    }

    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        //iv_logo.animate().translationYBy(-500f).duration = 2000
        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnableButton, 700)
        mDelayHandler!!.postDelayed(mRunnableText, 1200)
    }

    private fun initBinding() {
        mGetStartedBinding = DataBindingUtil.setContentView(this, R.layout.activity_get_started)

    }

    public fun load(view : View) {
        startActivity(
            Intent(
                this,
                ChooseOptionActivity::class.java
            )
        )
//        animateButtonWidth()
//        fadeOutTextAndShowProgressDialog()
        //nextAction()
    }

    private fun fadeOutTextAndShowProgressDialog() {
        mGetStartedBinding.tvGetStarted.animate().alpha(0f)
            .setDuration(250)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    showProgressDialog()
                }
            })
            .start()
    }

    private fun animateButtonWidth() {
        val anim = ValueAnimator.ofInt(mGetStartedBinding.flGetStarted.measuredWidth, getFabWidth().toInt())

        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            Log.d("GetStarted", "Value : " + value)
            val layoutParams = mGetStartedBinding.flGetStarted.layoutParams
            Log.d("GetStarted", "LayoutParams : " + mGetStartedBinding.flGetStarted.layoutParams)
            layoutParams.width = value
            mGetStartedBinding.flGetStarted.requestLayout()
        }
        anim.duration = 250
        anim.start()
    }

    private fun showProgressDialog() {
        mGetStartedBinding.progressBar.alpha = 1f
        mGetStartedBinding.run {
            progressBar
                .indeterminateDrawable
                .setColorFilter(parseColor("#ffffff"), PorterDuff.Mode.SRC_IN)
            progressBar.visibility = VISIBLE
        }
        Handler().postDelayed({
            mGetStartedBinding.progressBar.visibility = GONE
            mGetStartedBinding.flGetStarted.visibility = VISIBLE
        }, 2000)
    }

    private fun nextAction() {
        Handler().postDelayed({
            revealButton()

            fadeOutProgressDialog()

            delayedStartNextActivity()
        }, 2000)
    }

    private fun revealButton() {
        mGetStartedBinding.flGetStarted.setElevation(0f)

        mGetStartedBinding.reveal.visibility = VISIBLE

        val cx = mGetStartedBinding.reveal.width
        val cy = mGetStartedBinding.reveal.height


        val x = (getFabWidth() / 2 + mGetStartedBinding.flGetStarted.x).toInt()
        val y = (getFabWidth() / 2 + mGetStartedBinding.flGetStarted.y).toInt()

        val finalRadius = Math.max(cx, cy) * 1.2f

        val reveal = ViewAnimationUtils
            .createCircularReveal(mGetStartedBinding.reveal, x, y, getFabWidth(), finalRadius)

        reveal.duration = 350
        reveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                reset(animation)
                //                finish();
            }

            private fun reset(animation: Animator) {
                super.onAnimationEnd(animation)
                mGetStartedBinding.reveal.visibility = INVISIBLE
                mGetStartedBinding.tvGetStarted.visibility = VISIBLE
                mGetStartedBinding.tvGetStarted.alpha = 1f
                mGetStartedBinding.flGetStarted.elevation = 4f
                val layoutParams = mGetStartedBinding.flGetStarted.layoutParams
                layoutParams.width = (resources.displayMetrics.density * 330).toInt()
                mGetStartedBinding.flGetStarted.requestLayout()
            }
        })

        reveal.start()
    }

    private fun fadeOutProgressDialog() {
        mGetStartedBinding.progressBar.animate().alpha(0f).setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                }
            }).start()
    }

    private fun delayedStartNextActivity() {
        Handler().postDelayed({
            startActivity(
                Intent(
                    this,
                    ChooseOptionActivity::class.java
                )
            )
        }, 100)
    }

    private fun getFabWidth(): Float {
        return resources.getDimension(R.dimen.fab_size)
    }

    override fun onBackPressed() {
    }
}