package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.NotesDataModel
import io.reactivex.disposables.CompositeDisposable
import com.example.notesapp.NotesApp
import com.example.notesapp.dashboard.ui.home.HomeFragment
import com.example.notesapp.database.dbmodel.NotesDBModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesDBViewModel : ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    suspend fun insertAllNotes(
        notesDataModel: NotesDataModel
    ): Long {
        return NotesApp.getDataHandler()!!.insertAllNotes(notesDataModel)
    }

    fun getAllNotes(): LiveData<List<NotesDBModel>> {
        return NotesApp.getDataHandler()!!.getAllNotes()
    }

    fun getAllBookMarkNotes(isCheck: Boolean): LiveData<List<NotesDBModel>> {
        return NotesApp.getDataHandler()!!.getAllBookMarkNotes(isCheck)
    }

    suspend fun getBookMarkIds(isCheck: Boolean): List<String> {
        return NotesApp.getDataHandler()!!.getBookMarkIds(isCheck)
    }

    suspend fun getAddedToCartIds(isCheck: Boolean): List<String> {
        return NotesApp.getDataHandler()!!.getAddedToCartIds(isCheck)
    }

    fun updateBookMarkNotes(id: String, isBookMark: Boolean): Int {
        var result = 0
        viewModelScope.launch {
            result = NotesApp.getDataHandler()!!.updateBookMarkNotes(id, isBookMark)
        }
        return result
    }

    fun getAddedToCartNotes(isAddedToCart: Boolean): LiveData<List<NotesDBModel>> {
        return NotesApp.getDataHandler()!!.getAddedToCartNotes(isAddedToCart)
    }

    fun updateAddToCartNotes(id: String, isAddToCart: Boolean): Int {
        var result: Int = 0
        viewModelScope.launch {
            result = NotesApp.getDataHandler()!!.updateAddToCartNotes(id, isAddToCart)
        }
        return result
    }

    fun deleteAllNotes(): Int {
        var result: Int = 0
        viewModelScope.launch {
            result = NotesApp.getDataHandler()!!.deleteAllNotes()
        }
        return result
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