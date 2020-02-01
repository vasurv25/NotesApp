package com.example.notesapp.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
//    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        init()
    }

    abstract fun init()
}