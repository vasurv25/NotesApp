package com.example.notesapp.address

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.NotesApp
import com.example.notesapp.database.dbmodel.AddressDBModel
import com.example.notesapp.model.AddressDataModel
import io.reactivex.disposables.CompositeDisposable

class AddressViewModel: ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    private var mHouseNumber: String? = null
    private var mAddressLine1: String? = null
    private var mAddressLine2: String? = null
    private var mLandMark: String? = null
    private var mCity: String? = null
    private var mPinCode: String? = null
    var isHouseNumberValid = ObservableBoolean()
    var isAddressLine1Valid = ObservableBoolean()
    var isAddressLine2Valid = ObservableBoolean()
    var isLandMarkValid = ObservableBoolean()
    var isCityValid = ObservableBoolean()
    var isPinCodeValid = ObservableBoolean()

    val houseNumberWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mHouseNumber = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isHouseNumberValid.set(true)
                isHouseNumberValid.notifyChange()
            }
        }
    }

    val addressLine1Watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mAddressLine1 = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isAddressLine1Valid.set(true)
                isAddressLine1Valid.notifyChange()
            }
        }
    }

    val addressLine2Watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mAddressLine2 = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isAddressLine2Valid.set(true)
                isAddressLine2Valid.notifyChange()
            }
        }
    }

    val landMarkWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mLandMark = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isLandMarkValid.set(true)
                isLandMarkValid.notifyChange()
            }
        }
    }

    val cityWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mCity = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isCityValid.set(true)
                isCityValid.notifyChange()
            }
        }
    }

    val pinCodeWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(edit: CharSequence?, p1: Int, p2: Int, p3: Int) {
            mPinCode = edit.toString()
        }
        override fun afterTextChanged(edit: Editable?) {
            if (!edit.isNullOrEmpty()) {
                isPinCodeValid.set(true)
                isPinCodeValid.notifyChange()
            }
        }
    }

    suspend fun saveAddress(): Long {
        val addressDataModel = AddressDataModel()
        addressDataModel.houseNumber = mHouseNumber!!
        addressDataModel.addressLine1 = mAddressLine1!!
        addressDataModel.addressLine2 = mAddressLine2!!
        addressDataModel.landmark = mLandMark!!
        addressDataModel.city = mCity!!
        addressDataModel.pincode = mPinCode!!
        return NotesApp.getDataHandler()!!.insertAddress(addressDataModel)
    }

    fun getAddress(): LiveData<List<AddressDBModel>> {
        return NotesApp.getDataHandler()!!.getAddress()
    }

    suspend fun updateAddressSelection(id: Int, isSelected: Boolean): Int {
        return NotesApp.getDataHandler()!!.updateSelectedAddress(id, isSelected)
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