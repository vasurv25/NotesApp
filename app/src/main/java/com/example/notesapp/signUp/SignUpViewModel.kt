package com.example.notesapp.signUp

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.NotesApp
import com.example.notesapp.R
import com.example.notesapp.network.SignUpRequest
import com.example.notesapp.network.SignUpResponse
import com.example.utils.AuthTypes
import com.example.utils.isEmail
import com.example.utils.isValidPassword
import com.example.utils.isValidPhoneNumber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import retrofit2.Response

class SignUpViewModel :ViewModel() {

    private val TAG = "SignUpViewModel"

    private var mFirstName: String? = null
    private var mLastName: String? = null
    private var mEmail: String? = null
    private var mPassword: String? = null
    private var mNumber: String? = null
    var isEmailValid = ObservableBoolean()
    var isPasswordValid = ObservableBoolean()
    var isFirstNameValid = ObservableBoolean()
    var isLastNameValid = ObservableBoolean()
    var isNumberValid = ObservableBoolean()
    var mShowErrorSnackBar: MutableLiveData<String> = MutableLiveData()
    private var mSignUpRequest: SignUpRequest =
        SignUpRequest(null, "dfsd","sdfsd", null, 1, "9907088897", null,  AuthTypes.MOBILE)
    private var mSignUpResponse: MutableLiveData<Response<SignUpResponse>> = MutableLiveData()
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    val firstNameWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mFirstName = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isFirstNameValid.set(true)
                isFirstNameValid.notifyChange()
            }
        }
    }

    val lastNameWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mLastName = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isLastNameValid.set(true)
                isLastNameValid.notifyChange()
            }
        }
    }

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mEmail = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isEmailValid.set(true)
                isEmailValid.notifyChange()
            }
        }
    }

    val passwordWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mPassword = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isPasswordValid.set(true)
                isPasswordValid.notifyChange()
            }
        }
    }

    val numberWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mNumber = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isNumberValid.set(true)
                isNumberValid.notifyChange()
            }
        }
    }

    fun onSignUpClick(view: View) {
        Log.i(TAG, "onSignUpClick")
        isEmailValid.set(mEmail?.isNotEmpty() ?: false)
        isEmailValid.notifyChange()
        isPasswordValid.set(mPassword?.isNotEmpty() ?: false)
        isPasswordValid.notifyChange()
        isFirstNameValid.set(mFirstName?.isNotEmpty() ?: false)
        isFirstNameValid.notifyChange()
        isLastNameValid.set(mLastName?.isNotEmpty() ?: false)
        isLastNameValid.notifyChange()
        isNumberValid.set(mNumber?.isNotEmpty() ?: false)
        isNumberValid.notifyChange()
        if (isFirstNameValid.get() && isLastNameValid.get() && isEmailValid.get() && isNumberValid.get() && isPasswordValid.get()) {

            if (!mNumber!!.isValidPhoneNumber()) {
                mShowErrorSnackBar.value =
                    NotesApp.getInstance().getString(R.string.enter_phone_number)
            } else if (!mPassword!!.isValidPassword()) {
                mShowErrorSnackBar.value =
                    NotesApp.getInstance().getString(R.string.choose_password)
            } else if (!mEmail!!.isEmail()) {
                mShowErrorSnackBar.value =
                    NotesApp.getInstance().getString(R.string.enter_valid_email)
            } else {
                mSignUpRequest.firstName = mFirstName
                mSignUpRequest.email = mEmail
                mSignUpRequest.password = mPassword
                verifySignUp()
            }
        } else {
            mShowErrorSnackBar.value = NotesApp.getInstance().getString(R.string.empty_fields)
        }
    }

    /**
     * method to call API to verify login credentials
     */
    private fun verifySignUp() {
        val disposable = NotesApp.getApiService()!!.studentSignUp(mSignUpRequest)
            .subscribeOn(NotesApp.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                //isVisible.set(true)
            }
            .doFinally {
                //isVisible.set(false)
            }
            .subscribe({ response ->
                if (response.isSuccessful) {
                        if (response.body()?.data?.mobileNumber == null) {
                        mShowErrorSnackBar.value = response.body()?.error?.message
                    } else {
                        mSignUpResponse.value = response
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    mShowErrorSnackBar.value = jObjError.getString("message")
                }
            }, {
                Log.i(TAG, "error login")
                mShowErrorSnackBar.value = it.localizedMessage
            })
        mCompositeDisposable!!.add(disposable)
    }

    fun signUpResponse(): MutableLiveData<Response<SignUpResponse>> {
        return mSignUpResponse
    }

    private fun unSubscribeFromObservable() {
        if (mCompositeDisposable != null && !mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable!!.dispose()
        }
    }

    fun reset() {
        unSubscribeFromObservable()
        mCompositeDisposable = null
    }
}