package com.example.notesapp.forgetPassword

import android.content.Context
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notesapp.NotesAppSavedProfile
import com.example.notesapp.R
import com.example.notesapp.base.BaseActivity
import com.example.notesapp.databinding.ActivityForgetPasswordBinding
import com.example.notesapp.network.ForgetPasswordResponse
import com.example.utils.hideSoftKeyboard
import com.example.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.iv_background
import kotlinx.android.synthetic.main.activity_signup_otp.*
import retrofit2.Response

class ForgetPasswordActivity : BaseActivity(), View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    private lateinit var mActivityForgetPasswordBinding: ActivityForgetPasswordBinding
    private lateinit var mForgetPasswordViewModel: ForgetPasswordViewModel

    override fun init() {
        setPINListeners()
    }

    private fun setPINListeners() {

        pin_hidden_edittext.addTextChangedListener(this)
        et_otp_first.setOnKeyListener(this)
        et_otp_second.setOnKeyListener(this)
        et_otp_third.setOnKeyListener(this)
        et_otp_four.setOnKeyListener(this)
        et_otp_five.setOnKeyListener(this)
        et_otp_six.setOnKeyListener(this)
        pin_hidden_edittext.setOnKeyListener(this)

        et_otp_first.onFocusChangeListener = this
        et_otp_second.onFocusChangeListener = this
        et_otp_third.onFocusChangeListener = this
        et_otp_four.onFocusChangeListener = this
        et_otp_five.onFocusChangeListener = this
        et_otp_six.onFocusChangeListener = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()

        val animSlideUpStudent = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.move_up_second
        )
        iv_background.startAnimation(animSlideUpStudent)
        iv_background.visibility = View.VISIBLE
    }

    private fun initBinding() {
        mActivityForgetPasswordBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
        mActivityForgetPasswordBinding.clickHandler = this
        mForgetPasswordViewModel = ViewModelProviders.of(this).get(ForgetPasswordViewModel::class.java)
        mActivityForgetPasswordBinding.forgetPasswordViewModel = mForgetPasswordViewModel
        mForgetPasswordViewModel.forgetPasswordResposne().observe(this, Observer<Response<ForgetPasswordResponse>> { t ->
            NotesAppSavedProfile.savedDataModel?.otp = t.body()?.otpDetails?.smsOtp
            bt_fp_circular_progress.visibility = View.GONE
            bt_fp_circular_progress_verify.visibility = View.VISIBLE
            cl_number_layout.visibility = View.INVISIBLE
            cl_otp_layout.visibility = View.VISIBLE
            iv_number_edit_icon.visibility = View.VISIBLE
            tv_edit_number.visibility = View.VISIBLE
            layout_resend_text.visibility = View.VISIBLE
            tv_forget_password_msg.text = getString(R.string.verification_code_msg)
            tv_edit_number.text = "+91 - " + et_fp_number.text.toString()
            Toast.makeText(this, getString(R.string.toast_otp_success_msg), Toast.LENGTH_SHORT).show()
        })

        mForgetPasswordViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showErrorSnackBar(t.toString())
        })

        mForgetPasswordViewModel.mLoading.observe(this, Observer { t ->
            if (t == true) {
                bt_fp_circular_progress.startAnimation()
            } else {
                bt_fp_circular_progress.revertAnimation()
//                bt_login_circular_progress.revertAnimation {
////                    //revealButton()
////                    //fadeOutProgressDialog()
////                    delayStartNextActivity()
////                }
                //delayStartNextActivity()
            }
        })
    }

    private fun showErrorSnackBar(msg: String) {
        showSnackBar(
            cl_snackbar_forget_pass,
            msg,
            ContextCompat.getColor(this, R.color.white),
            resources
        )
    }

    fun onClick(view: View) {
            when (view.id) {
            R.id.bt_fp_circular_progress_verify -> {
                bt_fp_circular_progress.stopAnimation()
            }
            R.id.tv_fb_resend_click -> {
                mForgetPasswordViewModel.verifyForgetPassword(et_fp_number.text.toString())
            }
        }
    }

    private fun checkForValidOtp() {
        if (getFpOtpNumber() == NotesAppSavedProfile.savedDataModel?.otp.toString()) {

        } else {
            showErrorSnackBar(getString(R.string.invalid_otp))
        }
    }

    fun onEditNumberIconClick(view: View) {
        when (view.id) {
            R.id.iv_number_edit_icon -> {
                bt_fp_circular_progress.visibility = View.VISIBLE
                bt_fp_circular_progress_verify.visibility = View.GONE
                cl_number_layout.visibility = View.VISIBLE
                cl_otp_layout.visibility = View.GONE
                tv_edit_number.visibility = View.GONE
                iv_number_edit_icon.visibility = View.GONE
                layout_resend_text.visibility = View.GONE
                tv_forget_password_msg.text = resources.getString(R.string.you_will_get_an_otp_message)
                et_fp_number.text.clear()
            }
        }
    }

    private fun showSoftKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setFocus(editText: EditText?) {
        if (editText == null)
            return

        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length == 0) {
            et_otp_first.setText("")
        } else if (s?.length == 1) {
            et_otp_first.setText(s[0] + "")
            et_otp_second.setText("")
            et_otp_third.setText("")
            et_otp_four.setText("")
            et_otp_five.setText("")
            et_otp_six.setText("")
        } else if (s?.length == 2) {
            et_otp_second.setText(s[1] + "")
            et_otp_third.setText("")
            et_otp_four.setText("")
            et_otp_five.setText("")
            et_otp_six.setText("")
        } else if (s?.length == 3) {
            et_otp_third.setText(s[2] + "")
            et_otp_four.setText("")
            et_otp_five.setText("")
            et_otp_six.setText("")
        } else if (s?.length == 4) {
            et_otp_four.setText(s[3] + "")
            et_otp_five.setText("")
            et_otp_six.setText("")
        } else if (s?.length == 5) {
            et_otp_five.setText(s[4] + "")
            et_otp_six.setText("")
        } else if (s?.length == 6) {
            et_otp_six.setText(s[5] + "")

            hideSoftKeyboard(this)
        }

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        when(view?.id) {
            R.id.et_otp_first -> if (hasFocus) {
                setFocus(pin_hidden_edittext)
                showSoftKeyboard(pin_hidden_edittext)
            }
            R.id.et_otp_second -> if (hasFocus) {
                setFocus(pin_hidden_edittext)
                showSoftKeyboard(pin_hidden_edittext)
            }
            R.id.et_otp_third -> if (hasFocus) {
                setFocus(pin_hidden_edittext)
                showSoftKeyboard(pin_hidden_edittext)
            }
            R.id.et_otp_four -> if (hasFocus) {
                setFocus(pin_hidden_edittext)
                showSoftKeyboard(pin_hidden_edittext)
            }
            R.id.et_otp_five -> if (hasFocus) {
                setFocus(pin_hidden_edittext)
                showSoftKeyboard(pin_hidden_edittext)
            }
            R.id.et_otp_six -> if (hasFocus) {
                setFocus(pin_hidden_edittext)
                showSoftKeyboard(pin_hidden_edittext)
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action === KeyEvent.ACTION_DOWN) {
            val id = view?.id
            when (id) {
                R.id.pin_hidden_edittext -> if (keyCode === KeyEvent.KEYCODE_DEL) {
                    if (pin_hidden_edittext.text.length === 6)
                        et_otp_six.setText("")
                    else if (pin_hidden_edittext.text.length === 5)
                        et_otp_five.setText("")
                    else if (pin_hidden_edittext.text.length === 4)
                        et_otp_four.setText("")
                    else if (pin_hidden_edittext.text.length === 3)
                        et_otp_third.setText("")
                    else if (pin_hidden_edittext.text.length === 2)
                        et_otp_second.setText("")
                    else if (pin_hidden_edittext.text.length === 1)
                        et_otp_first.setText("")

                    if (pin_hidden_edittext.length() > 0)
                        pin_hidden_edittext.setText(
                            pin_hidden_edittext.text.subSequence(
                                0,
                                pin_hidden_edittext.length() - 1
                            )
                        )
                    return true
                }
                else -> return false
            }
        }

        return false
    }

    private fun getFpOtpNumber(): String {
        return pin_hidden_edittext.text.toString()
    }
}