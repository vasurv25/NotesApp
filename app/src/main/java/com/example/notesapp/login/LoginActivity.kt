package com.example.notesapp.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.databinding.ActivityLoginBinding
import com.example.notesapp.network.LoginResponse
import com.example.notesapp.signUp.SignUpActivity
import com.example.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mActivityLoginBinding: ActivityLoginBinding
    private lateinit var mLoginViewModel: LoginViewModel

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        makeSignUpClickable()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {

        }
    }

    private fun initBinding() {
        mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mActivityLoginBinding.clickHandler = this
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        mActivityLoginBinding.loginViewModel = mLoginViewModel
        mLoginViewModel.loginResponse().observe(this, Observer<Response<LoginResponse>> { t ->

        })

        mLoginViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showSnackBar(
                layout_login_parent,
                t.toString(),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.darkBlue)
            )
        })

        mLoginViewModel.isEmailValid.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!(sender as ObservableBoolean).get()) {
                    tIL_email.error = getString(R.string.enter_valid_email)
                } else {
                    tIL_email.error = null
                }
            }
        })
        mLoginViewModel.isPasswordValid.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (!(sender as ObservableBoolean).get()) {
                    tIL_password.error = getString(R.string.enter_valid_password)
                } else {
                    tIL_password.error = null
                }
            }
        })
    }


    private fun makeSignUpClickable() {
        val spannable = SpannableString(resources.getString(R.string.dont_have_an_account_sign_up))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                onNavigateToSignUpScreen()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(this@LoginActivity, R.color.orange)
            }
        }
        spannable.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_spannable_signUp.text = spannable
        tv_spannable_signUp.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun onNavigateToSignUpScreen() {
        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
    }
}