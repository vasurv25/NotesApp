package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.model.NotesDataModel
import io.reactivex.disposables.CompositeDisposable
import com.example.notesapp.NotesApp
import com.example.notesapp.dashboard.ui.home.HomeFragment
import com.example.notesapp.database.dbmodel.NotesDBModel

class NotesDBViewModel: ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    fun insertAllNotes(
        notesDataModel: NotesDataModel,
        listener: DBActivityListener
    ) {
        NotesApp.getDataHandler()!!.insertAllNotes(notesDataModel, listener)
    }

    fun getAllNotes() : LiveData<List<NotesDBModel>> {
        return NotesApp.getDataHandler()!!.getAllNotes()
    }

    fun getAllBookMarkNotes(isCheck: Boolean) : LiveData<List<NotesDBModel>> {
        return NotesApp.getDataHandler()!!.getAllBookMarkNotes(isCheck)
    }

    fun getBookMarkIds(isCheck: Boolean, listener: DBActivityListener) {
        NotesApp.getDataHandler()!!.getBookMarkIds(isCheck, listener)
    }

    fun updateBookMarkNotes(id: String, isBookMark: Boolean) {
        NotesApp.getDataHandler()!!.updateBookMarkNotes(id, isBookMark)
    }

    fun updateAddToCartNotes(id: String, isAddToCart: Boolean) {
        NotesApp.getDataHandler()!!.updateAddToCartNotes(id, isAddToCart)
    }

    fun deleteAllNotes() {
        NotesApp.getDataHandler()!!.deleteAllNotes()
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