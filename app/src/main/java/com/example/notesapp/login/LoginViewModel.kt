package com.example.notesapp.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.NotesApp.Companion.getApiService
import com.example.notesapp.NotesApp.Companion.subscribeScheduler
import com.example.notesapp.network.LoginRequest
import com.example.notesapp.network.LoginResponse
import com.example.utils.AuthTypes
import com.example.utils.isEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val TAG = "LoginViewModel"

    private var mEmail: String? = null
    private var mPassword: String? = null
    var isEmailValid = ObservableBoolean()
    var isPasswordValid = ObservableBoolean()
    var isVisible = ObservableBoolean()
    private var mLoginRequest: LoginRequest =
        LoginRequest(null, null, AuthTypes.MOBILE)
    private var mLoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    var mShowErrorSnackBar: MutableLiveData<String> = MutableLiveData()

    val phoneWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mEmail = edit.toString()
        }

        override fun afterTextChanged(edit: Editable?) {
            if (edit.toString().isEmail()) {
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

    fun onLoginClick(view: View) {
        Log.i(TAG, "onLoginClick")
        isEmailValid.set(mEmail?.isEmail() ?: false)
        isEmailValid.notifyChange()
        isPasswordValid.set(mPassword?.isNotEmpty() ?: false)
        isPasswordValid.notifyChange()
        if (isEmailValid.get() && isPasswordValid.get()) {
            mLoginRequest.username = mEmail
            mLoginRequest.password = mPassword
            verifyLogin()
        }
    }

    /**
     * method to call API to verify login credentials
     */
    private fun verifyLogin() {
        val disposable = getApiService()!!.studentLogin(mLoginRequest)
            .subscribeOn(subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                //isVisible.set(true)
            }
            .doFinally {
                //isVisible.set(false)
            }
            .subscribe({ response ->
                if (response.isSuccessful) {
                    if (response.body()?.data.isNullOrEmpty()) {
                        mShowErrorSnackBar.value = response.body()?.error?.message
                    } else {
                        mLoginResponse.value = response
                    }
                } else {
                    mShowErrorSnackBar.value = response.body()?.error?.message
                }
            }, {
                Log.i(TAG, "error login : " + it.localizedMessage)
                mShowErrorSnackBar.value = it.localizedMessage
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