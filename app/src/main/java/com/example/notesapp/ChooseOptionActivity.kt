package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.login.LoginActivity
import kotlinx.android.synthetic.main.activity_choose_option.*

class ChooseOptionActivity:BaseActivity() {
    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_option)
        Handler().postDelayed({
            val animSlideDownStudent = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.fade_out)
            iv_cp_student.startAnimation(animSlideDownStudent)
            val animSlideDownFaculty = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.fade_out)
            iv_cp_faculty.startAnimation(animSlideDownFaculty)
            iv_cp_student.visibility = View.VISIBLE
            iv_cp_faculty.visibility = View.VISIBLE
        },500)
    }

    public fun navigateToStudentLogin(view: View) {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}