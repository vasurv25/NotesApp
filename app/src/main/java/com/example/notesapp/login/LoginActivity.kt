package com.example.notesapp.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.forgetPassword.ForgetPasswordActivity
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import androidx.core.util.Pair
import androidx.databinding.adapters.ViewGroupBindingAdapter
import androidx.lifecycle.Observer
import com.example.notesapp.GetStartedActivity
import com.example.notesapp.network.LoginResponse
import com.example.notesapp.signUp.SignUpActivity
import com.example.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Response
import kotlin.math.max

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mActivityLoginBinding: ActivityLoginBinding
    private lateinit var mLoginViewModel: LoginViewModel

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        makeLoginClickable()
    }

    override fun onStart() {
        super.onStart()
        animateLoginView()
    }

    private fun animateLoginView() {
        val animLeftInEllipse = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.left_in
        )
        iv_ellipse.startAnimation(animLeftInEllipse)

        val animRightInRect = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.right_in
        )
        iv_rectangle.startAnimation(animRightInRect)

        val animSlideUpStudent = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.move_up
        )
        iv_student_login.startAnimation(animSlideUpStudent)

        iv_ellipse.visibility = View.VISIBLE
        iv_rectangle.visibility = View.VISIBLE
        iv_student_login.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_forget_password -> {
                val intent = Intent(applicationContext, ForgetPasswordActivity::class.java)
                val pairEllipseTransition: Pair<View, String> =
                    Pair.create(iv_ellipse, resources.getString(R.string.transition_ellipse))
                val pairRectTransition: Pair<View, String> =
                    Pair.create(iv_rectangle, resources.getString(R.string.transition_rect))

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    pairEllipseTransition, pairRectTransition
                )
                startActivity(intent, options.toBundle())
            }
            R.id.fl_get_started -> {

            }
        }
    }

     fun onPasswordToggleClick(view: View?) {
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

    @SuppressLint("ClickableViewAccessibility")
    private fun initBinding() {
        mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mActivityLoginBinding.clickHandler = this
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        mActivityLoginBinding.loginViewModel = mLoginViewModel
        mLoginViewModel.loginResponse().observe(this, Observer<Response<LoginResponse>> { t ->

        })

        mLoginViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showSnackBar(
                cl_snackbar_login,
                t.toString(),
                ContextCompat.getColor(this, R.color.white),
                resources
            )
        })

        mLoginViewModel.mLoading.observe(this, Observer { t ->
            if (t == true) {
                bt_login_circular_progress.startAnimation()
            } else {
                bt_login_circular_progress.stopAnimation()
//                bt_login_circular_progress.revertAnimation {
////                    //revealButton()
////                    //fadeOutProgressDialog()
////                    delayStartNextActivity()
////                }
                //delayStartNextActivity()
            }
        })

//        et_password.setOnTouchListener { view, event ->
//            val isOutsideView = event!!.x < 0 ||
//                    event.x > view!!.width ||
//                    event.y < 0 ||
//                    event.y > view.height;
//
//            // change input type will reset cursor position, so we want to save it
//            val cursor = et_password.selectionStart
//
//            if (isOutsideView || MotionEvent.ACTION_UP == event.action) {
//                et_password.inputType = InputType.TYPE_CLASS_TEXT
//                InputType.TYPE_TEXT_VARIATION_PASSWORD
//            }  else {
//                et_password.inputType = InputType.TYPE_CLASS_TEXT
//                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            }
//
//            et_password.setSelection(cursor)
//            true
//        }
//        mLoginViewModel.loginResponse().observe(this, Observer<Response<LoginResponse>> { t ->
//
//        })

        /*mLoginViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showSnackBar(
                layout_login_parent,
                t.toString(),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.darkBlue)
            )
        })*/

        /*mLoginViewModel.isEmailValid.addOnPropertyChangedCallback(object :
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
    }*/
    }

    private fun makeLoginClickable() {
        val spannable = SpannableString(resources.getString(R.string.dont_have_an_account_sign_up))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                navigateToSignUp()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(this@LoginActivity, R.color.orange)
            }
        }
        spannable.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_login_spannable.text = spannable
        tv_login_spannable.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        bt_login_circular_progress.dispose()
    }

    private fun revealButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bt_login_circular_progress.elevation = 0f
        }
        reveal_view_login.visibility = View.VISIBLE

        val x = reveal_view_login.width
        val y = reveal_view_login.height

        val startX = (getFinalWidth() / 2 + bt_login_circular_progress.x).toInt()
        val startY = (getFinalWidth() / 2 + bt_login_circular_progress.y).toInt()

        val radius = max(x, y) * 1.2f

        var reveal: Animator? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            reveal = ViewAnimationUtils.createCircularReveal(
                reveal_view_login,
                startX,
                startY,
                getFinalWidth().toFloat(),
                radius
            )
        }
        reveal!!.duration = 350
        reveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                finish()
            }
        })

        reveal.start()
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
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, bt_login_circular_progress, "")
        startActivity(intent, options.toBundle())
    }

    private fun fadeOutProgressDialog() {
        bt_login_circular_progress.animate().alpha(0f).setDuration(200).start();
    }

    private fun getFinalWidth(): Int {
        return resources.getDimension(R.dimen.get_width).toInt()
    }


//    private fun slideToAbove() {
//        var slide = TranslateAnimation(
//            Animation.RELATIVE_TO_SELF, 0.0f,
//            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//            0.0f, Animation.RELATIVE_TO_SELF, -5.0f
//        );
//
//        slide.duration = 3000;
//        slide.fillAfter = true;
//        slide.isFillEnabled = true;
//        iv_student_login.startAnimation(slide);
//
//        slide.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(p0: Animation?) {
//
//            }
//
//            override fun onAnimationStart(p0: Animation?) {
//            }
//
//            override fun onAnimationEnd(p0: Animation?) {
//                iv_student_login.clearAnimation()
//
//                var layoutParams = ConstraintLayout.LayoutParams(
//                    iv_student_login.width, iv_student_login.height
//                );
//                // lp.setMargins(0, 0, 0, 0)
//                layoutParams.bottomToTop()
//                iv_student_login.layoutParams = layoutParams
//            }
//        })
//
//    }


}