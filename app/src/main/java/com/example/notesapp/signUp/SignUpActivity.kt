package com.example.notesapp.signUp

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
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.login.LoginActivity
import com.example.notesapp.login.LoginViewModel
import com.example.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.databinding.ActivitySignupBinding
import com.example.notesapp.network.LoginResponse
import com.example.notesapp.network.SignUpResponse
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    private lateinit var mSignUpViewModel: SignUpViewModel
    private lateinit var mActivitySignupBinding: ActivitySignupBinding

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        makeLoginClickable()
    }

    private fun initBinding() {
        mActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        mActivitySignupBinding.clickHandler = this
        mSignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        mActivitySignupBinding.signUpModel = mSignUpViewModel
        mSignUpViewModel.signUpResponse().observe(this, Observer<Response<SignUpResponse>> { t ->

        })

        mSignUpViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showSnackBar(
                layout_signUp_parent,
                t.toString(),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.darkBlue)
            )
        })

//        mSignUpViewModel.isFirstNameValid.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                if (!(sender as ObservableBoolean).get()) {
//                    tIL_first_name.error = getString(R.string.enter_valid_name)
//
//                } else {
//                    tIL_first_name.error = null
//                }
//            }
//        })
//
//        mSignUpViewModel.isEmailValid.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                if (!(sender as ObservableBoolean).get()) {
//                    tIL_signUp_email.error = getString(R.string.enter_valid_email)
//                } else {
//                    tIL_signUp_email.error = null
//                }
//            }
//        })
//        mSignUpViewModel.isPasswordValid.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                if (!(sender as ObservableBoolean).get()) {
//                    tIL_signUp_password.error = getString(R.string.enter_valid_password)
//                } else {
//                    tIL_signUp_password.error = null
//                }
//            }
//        })
    }

    private fun makeLoginClickable() {
        val spannable = SpannableString(resources.getString(R.string.already_have_an_account))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                finish()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(this@SignUpActivity, R.color.orange)
            }
        }
        spannable.setSpan(clickableSpan, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_spannable_login.text = spannable
        tv_spannable_login.movementMethod = LinkMovementMethod.getInstance()
    }
}