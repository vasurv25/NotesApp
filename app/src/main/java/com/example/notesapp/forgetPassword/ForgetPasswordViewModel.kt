package com.example.notesapp.forgetPassword

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.NotesApp
import com.example.notesapp.R
import com.example.notesapp.network.ForgetPasswordResponse
import com.example.utils.AuthTypes
import com.example.utils.validatePhoneNumber
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response

class ForgetPasswordViewModel: ViewModel() {

    var mLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var mForgetPasswordResponse: MutableLiveData<Response<ForgetPasswordResponse>> = MutableLiveData()
    var mShowErrorSnackBar: MutableLiveData<String> = MutableLiveData()
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var mNumber: String? = null
    var isNumberValid = ObservableBoolean()

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

    fun onSendOtpClick(view: View) {
        isNumberValid.set(mNumber?.isNotEmpty() ?: false)
        isNumberValid.notifyChange()
        if (isNumberValid.get()) {
            if (!validatePhoneNumber(mNumber)) {
                mShowErrorSnackBar.value =
                    NotesApp.getInstance().getString(R.string.enter_phone_number)
            } else {
                verifyForgetPassword(mNumber)
            }
        } else {
            mShowErrorSnackBar.value = NotesApp.getInstance().getString(R.string.empty_fields)
        }
    }

    fun verifyForgetPassword(number: String?) {
        val disposable = NotesApp.getApiService()!!.forgetPassword(number!!, AuthTypes.MOBILE)
            .subscribeOn(NotesApp.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mLoading.value = true
            }
            .doFinally {
                mLoading.value = false
            }
            .subscribe({ response ->
                if (response.isSuccessful) {
                    if (response.body()?.otpDetails?.mailOtp != null && response.body()?.otpDetails?.smsOtp != null) {
                        mForgetPasswordResponse.value = response
                    } else {
                        mShowErrorSnackBar.value = response.body()?.error?.message
                    }
                } else {
                    mShowErrorSnackBar.value = response.body()?.error?.message
                }
            }, {
                Log.i("ForgetPasswordViewModel", "error forget password : " + it.message)
                mShowErrorSnackBar.value = it.message
            })
        mCompositeDisposable!!.add(disposable)
    }

    fun forgetPasswordResposne(): MutableLiveData<Response<ForgetPasswordResponse>> {
        return mForgetPasswordResponse
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