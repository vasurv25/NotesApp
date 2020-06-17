package com.example.notesapp.signUp

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.example.notesapp.base.BaseActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivitySignupBinding
import com.example.notesapp.login.TestActivity
import com.example.notesapp.network.SavedDataModel
import com.example.notesapp.network.SignUpResponse
import com.example.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.et_password
import kotlinx.android.synthetic.main.activity_signup.iv_password_toggle
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    private lateinit var mSignUpViewModel: SignUpViewModel
    private lateinit var mActivitySignupBinding: ActivitySignupBinding
    private lateinit var mSavedDataModel: SavedDataModel

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        makeLoginClickable()
    }

    override fun onStart() {
        super.onStart()
        animationSignUpLogo()
    }

    private fun animationSignUpLogo() {
        val animSlideUpStudent = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.move_up
        )
        val animLeftInEllipse = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.left_in
        )
        val animRightInRect = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.right_in
        )
        iv_student_signup.startAnimation(animSlideUpStudent)
        iv_sign_up_ellipse.startAnimation(animLeftInEllipse)
        iv_sign_up_rectangle.startAnimation(animRightInRect)
        iv_student_signup.visibility = View.VISIBLE
        iv_sign_up_ellipse.visibility = View.VISIBLE
        iv_sign_up_rectangle.visibility = View.VISIBLE
    }

    private fun initBinding() {
        mActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        mActivitySignupBinding.clickHandler = this
        mSignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        mActivitySignupBinding.signUpModel = mSignUpViewModel
        mSignUpViewModel.signUpResponse().observe(this, Observer<Response<SignUpResponse>> { t ->
            navigateToSignUpOtpScreen()
        })

        mSignUpViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showSnackBar(
                cl_snackbar_signUp,
                t.toString(),
                ContextCompat.getColor(this, R.color.white),
                resources
            )
        })

        mSignUpViewModel.mLoading.observe(this, Observer { t ->
            if (t == true) {
                bt_signUp_circular_progress.startAnimation()
            } else {
                bt_signUp_circular_progress.revertAnimation()
//                bt_login_circular_progress.revertAnimation {
////                    //revealButton()
////                    //fadeOutProgressDialog()
////                    delayStartNextActivity()
////                }
//                delayStartNextActivity()
            }
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

    private fun navigateToSignUpOtpScreen() {
        val intent = Intent(applicationContext, SignUpOtpActivity::class.java)
        val pairEllipseTransition: Pair<View, String> =
            Pair.create(iv_sign_up_ellipse, resources.getString(R.string.transition_ellipse))
        val pairRectTransition: Pair<View, String> =
            Pair.create(iv_sign_up_rectangle, resources.getString(R.string.transition_rect))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            pairEllipseTransition, pairRectTransition
        )
        startActivity(intent, options.toBundle())
    }

    public fun onPasswordToggleClick(view: View?) {
        if (view?.id == R.id.iv_password_toggle) {

            if (et_password.transformationMethod == PasswordTransformationMethod.getInstance()) {
                iv_password_toggle.setImageResource(R.drawable.icon_eye_enable)


                //Show Password

                et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            } else {
                iv_password_toggle.setImageResource(R.drawable.icon_eye_disable)

                //Hide Password
                et_password.transformationMethod = PasswordTransformationMethod.getInstance()

            }
        }
    }

    private fun delayStartNextActivity() {
//        Handler().postDelayed({
////            startActivity(
////                Intent(
////                    this,
////                    TestActivity::class.java
////                )
////            )
////        }, 100)
        val intent = Intent(applicationContext, TestActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, bt_signUp_circular_progress, "")
        startActivity(intent, options.toBundle())
    }

    private fun makeLoginClickable() {
        val spannable = SpannableString(
            resources.getString(R.string.already_have_an_account))
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
        tv_signup_spannable.text = spannable
        tv_signup_spannable.movementMethod = LinkMovementMethod.getInstance()
    }
}