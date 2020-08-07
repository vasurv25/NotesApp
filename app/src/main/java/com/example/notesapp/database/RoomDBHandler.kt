package com.example.notesapp.database

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.notesapp.database.dbmodel.NotesDBModel
import com.example.notesapp.model.NotesDataModel

class RoomDBHandler(context: Context) {

    private var appRoomDatabase = NotesAppRoomDatabase.getInstance(context)

    fun insertAllNotes(notesDataModel: NotesDataModel, listener: DBActivityListener) {
        class InsertTask : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void) {
                val notes = NotesDBModel(
                    notesDataModel.id,
                    notesDataModel.subjectName,
                    notesDataModel.author,
                    notesDataModel.rating,
                    notesDataModel.price,
                    notesDataModel.bookMarked,
                    notesDataModel.addedToCart
                )
                appRoomDatabase.bookMarkNotesDao().insertAllNotes(notes)
            }
            override fun onPostExecute(result: Unit) {
                super.onPostExecute(result)
                listener.insertAllNotesUpdate()
            }
        }
        InsertTask().execute()
    }

    fun getAllNotes() : LiveData<List<NotesDBModel>> {
        return appRoomDatabase.bookMarkNotesDao().getAllNotes()
    }

    fun getAllBookMarkNotes(isCheck: Boolean): LiveData<List<NotesDBModel>> {
        return appRoomDatabase.bookMarkNotesDao().getBookMarkNotes(isCheck)
    }

    fun getBookMarkIds(isCheck: Boolean, listener: DBActivityListener) {
        class InsertTask : AsyncTask<Void, Unit, List<String>>() {
            override fun doInBackground(vararg params: Void): List<String> {
                return appRoomDatabase.bookMarkNotesDao().getBookMarkIds(isCheck)
            }
            override fun onPostExecute(result: List<String>) {
                super.onPostExecute(result)
                listener.getBookMarkIdUpdate(result)
            }
        }
        InsertTask().execute()
    }

    fun updateBookMarkNotes(id : String, isBookMark : Boolean) {
        class InsertTask : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void) {
                appRoomDatabase.bookMarkNotesDao().updateBookMarkNotes(id, isBookMark)
            }
        }
        InsertTask().execute()
    }

    fun updateAddToCartNotes(id : String, isAddedToCart : Boolean) {
        class InsertTask : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void) {
                appRoomDatabase.bookMarkNotesDao().updateBookMarkNotes(id, isAddedToCart)
            }
        }
        InsertTask().execute()
    }

    fun deleteAllNotes() {
        AsyncTask.execute{
            appRoomDatabase.bookMarkNotesDao().deleteAllNotes()
        }
    }
}