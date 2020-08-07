package com.example.notesapp.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.NotesApp
import com.example.notesapp.NotesApp.Companion.getApiService
import com.example.notesapp.NotesApp.Companion.subscribeScheduler
import com.example.notesapp.R
import com.example.notesapp.network.LoginRequest
import com.example.notesapp.network.LoginResponse
import com.example.utils.AuthTypes
import com.example.utils.isEmail
import com.example.utils.isValidPhoneNumber
import com.example.utils.validatePhoneNumber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val TAG = "LoginViewModel"

    private var mUserName: String? = null
    private var mPassword: String? = null
    var isUserNameValid = ObservableBoolean()
    var isPasswordValid = ObservableBoolean()
    var isVisible = ObservableBoolean()
    private var mLoginRequest: LoginRequest =
        LoginRequest(null, null, AuthTypes.MOBILE)
    private var mLoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    var mShowErrorSnackBar: MutableLiveData<String> = MutableLiveData()
    var mLoading: MutableLiveData<Boolean> = MutableLiveData()

    val emailWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mUserName = edit.toString()
        }

        override fun afterTextChanged(edit: Editable?) {
            if (edit.toString().isEmail()) {
                isUserNameValid.set(true)
                isUserNameValid.notifyChange()
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

    fun onLoginClick(view: View) {
        Log.i(TAG, "onLoginClick")
        isUserNameValid.set(mUserName?.isNotEmpty() ?: false)
        isUserNameValid.notifyChange()

        isPasswordValid.set(mPassword?.isNotEmpty() ?: false)
        isPasswordValid.notifyChange()

        if (isUserNameValid.get() && isPasswordValid.get()) {
            mLoginRequest.username = mUserName
            mLoginRequest.password = mPassword
            verifyLogin(view)
        } else {
            mShowErrorSnackBar.value = NotesApp.getInstance().getString(R.string.empty_fields)
        }
    }

    /**
     * method to call API to verify login credentials
     */
    private fun verifyLogin(view: View) {
        val disposable = getApiService()!!.studentLogin(mLoginRequest)
            .subscribeOn(subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mLoading.value = true
            }
            .doFinally {
                mLoading.value = false
            }
            .subscribe({ response ->
                if (response.isSuccessful) {
                    if (response.body()?.data?.token != null) {
                        mLoginResponse.value = response
                    } else {
                        mShowErrorSnackBar.value = response.body()?.error?.message
                    }
                } else {
                    mShowErrorSnackBar.value = response.body()?.error?.message
                }
            }, {
                Log.i(TAG, "error login : " + it.message)
                mShowErrorSnackBar.value = it.message
            })
        mCompositeDisposable!!.add(disposable)
    }

    fun loginResponse(): MutableLiveData<Response<LoginResponse>> {
        return mLoginResponse
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