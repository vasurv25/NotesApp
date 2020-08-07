package com.example.notesapp.signUp

import android.content.Context
import android.os.Bundle
import android.text.*
import android.view.KeyEvent
import android.view.View
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
import com.example.notesapp.databinding.ActivitySignupOtpBinding
import com.example.notesapp.model.SavedDataModel
import com.example.notesapp.network.UpdateMobileNumberResponse
import com.example.utils.hideSoftKeyboard
import com.example.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_signup_otp.*
import retrofit2.Response

class SignUpOtpActivity : BaseActivity(), View.OnFocusChangeListener, View.OnKeyListener,
    TextWatcher {

    private lateinit var mSignupOtpBinding: ActivitySignupOtpBinding
    private lateinit var mSignUpViewModel: SignUpViewModel
    private lateinit var mSavedDataModel: SavedDataModel

    override fun init() {
        setPINListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        //mSavedDataModel  = intent.getSerializableExtra(KEY_SIGN_UP_DETAILS) as SavedDataModel
    }

    private fun initBinding() {
        mSignupOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup_otp)
        mSignupOtpBinding.clickHandler = this
        mSignUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        mSignupOtpBinding.signUpModel = mSignUpViewModel
        mSignUpViewModel.updateNumberResponse().observe(this, Observer<Response<UpdateMobileNumberResponse>> { t ->
            NotesAppSavedProfile.savedDataModel?.number = t.body()?.updateMobile?.newMobileNumber
            NotesAppSavedProfile.savedDataModel?.otp = t.body()?.updateMobile?.smsOtp
            bt_signup_otp_circular_progress.visibility = View.GONE
            bt_signUp_otp_circular_progress_verify.visibility = View.VISIBLE
            cl_sign_up_number_layout.visibility = View.INVISIBLE
            cl_sign_up_otp_layout.visibility = View.VISIBLE
            iv_signup_otp_number_edit_icon.visibility = View.VISIBLE
            tv_signup_otp_edit_number.visibility = View.VISIBLE
            layout_signUp_resend_text.visibility = View.VISIBLE
            tv_signup_otp_msg.text = getString(R.string.verification_code_msg)
            tv_signup_otp_edit_number.text = "+91 - " + et_sign_up_number.text.toString()
            Toast.makeText(this, getString(R.string.toast_otp_success_msg), Toast.LENGTH_SHORT).show()
        })

        mSignUpViewModel.mShowErrorSnackBar.observe(this, Observer { t ->
            showErrorSnackBar(t.toString())
        })

        mSignUpViewModel.mLoading.observe(this, Observer { t ->
            if (t == true) {
                bt_signup_otp_circular_progress.startAnimation()
            } else {
                bt_signup_otp_circular_progress.revertAnimation()
            }
        })
        tv_signup_otp_edit_number.text = "+91 - " + NotesAppSavedProfile.savedDataModel?.number
    }

    private fun showErrorSnackBar(msg: String) {
        showSnackBar(
            cl_snackbar_signUp_otp,
            msg,
            ContextCompat.getColor(this, R.color.white),
            resources
        )
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.tv_signUp_resend_click -> {
                NotesAppSavedProfile.savedDataModel?.let {
                    mSignUpViewModel.updateMobileNumber(it.studentId, it.number)
                }
            }
            R.id.bt_signUp_otp_circular_progress_verify -> {
                checkForValidOtp()
            }
        }
    }

    private fun checkForValidOtp() {
        if (getOtpNumber() == NotesAppSavedProfile.savedDataModel?.otp.toString()) {

        } else {
            showErrorSnackBar(getString(R.string.invalid_otp))
        }
    }

    fun onEditNumberIconClick(view: View) {
        when (view.id) {
            R.id.iv_signup_otp_number_edit_icon -> {
                bt_signup_otp_circular_progress.visibility = View.VISIBLE
                bt_signUp_otp_circular_progress_verify.visibility = View.GONE
                cl_sign_up_number_layout.visibility = View.VISIBLE
                cl_sign_up_otp_layout.visibility = View.GONE
                tv_signup_otp_edit_number.visibility = View.GONE
                iv_signup_otp_number_edit_icon.visibility = View.GONE
                layout_signUp_resend_text.visibility = View.GONE
                tv_signup_otp_msg.text = resources.getString(R.string.verify_number_msg)
                et_sign_up_number.text.clear()
            }
        }
    }

    private fun setPINListeners() {

        pin_hidden_sign_up_edittext.addTextChangedListener(this)
        et_sign_up_otp_first.setOnKeyListener(this)
        et_sign_up_otp_second.setOnKeyListener(this)
        et_sign_up_otp_third.setOnKeyListener(this)
        et_sign_up_otp_four.setOnKeyListener(this)
        et_sign_up_otp_five.setOnKeyListener(this)
        et_sign_up_otp_six.setOnKeyListener(this)
        pin_hidden_sign_up_edittext.setOnKeyListener(this)

        et_sign_up_otp_first.onFocusChangeListener = this
        et_sign_up_otp_second.onFocusChangeListener = this
        et_sign_up_otp_third.onFocusChangeListener = this
        et_sign_up_otp_four.onFocusChangeListener = this
        et_sign_up_otp_five.onFocusChangeListener = this
        et_sign_up_otp_six.onFocusChangeListener = this
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
            et_sign_up_otp_first.setText("")
        } else if (s?.length == 1) {
            et_sign_up_otp_first.setText(s[0] + "")
            et_sign_up_otp_second.setText("")
            et_sign_up_otp_third.setText("")
            et_sign_up_otp_four.setText("")
            et_sign_up_otp_five.setText("")
            et_sign_up_otp_six.setText("")
        } else if (s?.length == 2) {
            et_sign_up_otp_second.setText(s[1] + "")
            et_sign_up_otp_third.setText("")
            et_sign_up_otp_four.setText("")
            et_sign_up_otp_five.setText("")
            et_sign_up_otp_six.setText("")
        } else if (s?.length == 3) {
            et_sign_up_otp_third.setText(s[2] + "")
            et_sign_up_otp_four.setText("")
            et_sign_up_otp_five.setText("")
            et_sign_up_otp_six.setText("")
        } else if (s?.length == 4) {
            et_sign_up_otp_four.setText(s[3] + "")
            et_sign_up_otp_five.setText("")
            et_sign_up_otp_six.setText("")
        } else if (s?.length == 5) {
            et_sign_up_otp_five.setText(s[4] + "")
            et_sign_up_otp_six.setText("")
        } else if (s?.length == 6) {
            et_sign_up_otp_six.setText(s[5] + "")

            hideSoftKeyboard(this)
        }

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        when(view?.id) {
            R.id.et_sign_up_otp_first -> if (hasFocus) {
                setFocus(pin_hidden_sign_up_edittext)
                showSoftKeyboard(pin_hidden_sign_up_edittext)
            }
            R.id.et_sign_up_otp_second -> if (hasFocus) {
                setFocus(pin_hidden_sign_up_edittext)
                showSoftKeyboard(pin_hidden_sign_up_edittext)
            }
            R.id.et_sign_up_otp_third -> if (hasFocus) {
                setFocus(pin_hidden_sign_up_edittext)
                showSoftKeyboard(pin_hidden_sign_up_edittext)
            }
            R.id.et_sign_up_otp_four -> if (hasFocus) {
                setFocus(pin_hidden_sign_up_edittext)
                showSoftKeyboard(pin_hidden_sign_up_edittext)
            }
            R.id.et_sign_up_otp_five -> if (hasFocus) {
                setFocus(pin_hidden_sign_up_edittext)
                showSoftKeyboard(pin_hidden_sign_up_edittext)
            }
            R.id.et_sign_up_otp_six -> if (hasFocus) {
                setFocus(pin_hidden_sign_up_edittext)
                showSoftKeyboard(pin_hidden_sign_up_edittext)
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action === KeyEvent.ACTION_DOWN) {
            val id = view?.id
            when (id) {
                R.id.pin_hidden_edittext -> if (keyCode === KeyEvent.KEYCODE_DEL) {
                    if (pin_hidden_sign_up_edittext.text.length === 6)
                        et_sign_up_otp_six.setText("")
                    else if (pin_hidden_sign_up_edittext.text.length === 5)
                        et_sign_up_otp_five.setText("")
                    else if (pin_hidden_sign_up_edittext.text.length === 4)
                        et_sign_up_otp_four.setText("")
                    else if (pin_hidden_sign_up_edittext.text.length === 3)
                        et_sign_up_otp_third.setText("")
                    else if (pin_hidden_sign_up_edittext.text.length === 2)
                        et_sign_up_otp_second.setText("")
                    else if (pin_hidden_sign_up_edittext.text.length === 1)
                        et_sign_up_otp_first.setText("")

                    if (pin_hidden_sign_up_edittext.length() > 0)
                        pin_hidden_sign_up_edittext.setText(
                            pin_hidden_sign_up_edittext.text.subSequence(
                                0,
                                pin_hidden_sign_up_edittext.length() - 1
                            )
                        )
                    return true
                }
                else -> return false
            }
        }

        return false
    }

    private fun getOtpNumber(): String {
        return pin_hidden_sign_up_edittext.text.toString()
    }

    override fun onBackPressed() {
    }
}