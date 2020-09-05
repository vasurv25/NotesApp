package com.example.notesapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notesapp.database.dbmodel.AddressDBModel
import com.example.notesapp.database.dbmodel.NotesDBModel
import com.example.notesapp.model.AddressDataModel
import com.example.notesapp.model.NotesDataModel

class RoomDBHandler(context: Context) {

    private var appRoomDatabase = NotesAppRoomDatabase.getInstance(context)

    suspend fun insertAllNotes(notesDataModel: NotesDataModel): Long {
        val notes = NotesDBModel(
            notesDataModel.id,
            notesDataModel.subjectName,
            notesDataModel.author,
            notesDataModel.rating,
            notesDataModel.price,
            notesDataModel.bookMarked,
            notesDataModel.addedToCart
        )
        return appRoomDatabase.bookMarkNotesDao().insertAllNotes(notes)
    }

    fun getAllNotes() : LiveData<List<NotesDBModel>> {
        return appRoomDatabase.bookMarkNotesDao().getAllNotes()
    }

    fun getAllBookMarkNotes(isCheck: Boolean): LiveData<List<NotesDBModel>> {
        return appRoomDatabase.bookMarkNotesDao().getBookMarkNotes(isCheck)
    }

    fun getAddedToCartNotes(isCheck: Boolean): LiveData<List<NotesDBModel>> {
        return appRoomDatabase.bookMarkNotesDao().getAddedToCartNotes(isCheck)
    }

    suspend fun getBookMarkIds(isCheck: Boolean): List<String> {
        return appRoomDatabase.bookMarkNotesDao().getBookMarkIds(isCheck)

    }

    suspend fun getAddedToCartIds(isCheck: Boolean): List<String> {
        return appRoomDatabase.bookMarkNotesDao().getAddedToCartIds(isCheck)
    }

    suspend fun updateBookMarkNotes(id : String, isBookMark : Boolean): Int {
        return appRoomDatabase.bookMarkNotesDao().updateBookMarkNotes(id, isBookMark)
    }

    suspend fun updateAddToCartNotes(id : String, isAddedToCart : Boolean): Int {
        return appRoomDatabase.bookMarkNotesDao().updateAddToCartNotes(id, isAddedToCart)
    }

    suspend fun deleteAllNotes(): Int {
        return appRoomDatabase.bookMarkNotesDao().deleteAllNotes()
    }

    suspend fun insertAddress(addressDataModel: AddressDataModel): Long {
        val address = AddressDBModel()
        address.houseNumber = addressDataModel.houseNumber
        address.addressLine1 = addressDataModel.addressLine1
        address.addressLine2 = addressDataModel.addressLine2
        address.landmark = addressDataModel.landmark
        address.city = addressDataModel.city
        address.pincode = addressDataModel.pincode
        address.isSelected = addressDataModel.isSelected
        return appRoomDatabase.addressDao().insertAddress(address)
    }

    fun getAddress() : LiveData<List<AddressDBModel>> {
        return appRoomDatabase.addressDao().getAddress()
    }

    suspend fun updateSelectedAddress(id: Int, isSelected: Boolean): Int {
        return appRoomDatabase.addressDao().updateSelectedAddress(id, isSelected)
    }
}