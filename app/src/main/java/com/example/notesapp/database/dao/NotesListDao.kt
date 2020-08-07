package com.example.notesapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.database.dbmodel.NotesDBModel

@Dao
interface NotesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNotes(notesDBModel: NotesDBModel)

    @Transaction
    @Query("Select * from NOTES_LIST_TABLE")
    fun getAllNotes(): LiveData<List<NotesDBModel>>

    @Transaction
    @Query("Select * from NOTES_LIST_TABLE where bookMarked = :isCheck")
    fun getBookMarkNotes(isCheck: Boolean): LiveData<List<NotesDBModel>>

    @Transaction
    @Query("Select _id from NOTES_LIST_TABLE where bookMarked = :isCheck")
    fun getBookMarkIds(isCheck: Boolean): List<String>

    @Query("DELETE FROM NOTES_LIST_TABLE")
    fun deleteAllNotes()

    @Query("UPDATE NOTES_LIST_TABLE SET bookMarked= :bookMarked where _id = :id")
    fun updateBookMarkNotes(id: String, bookMarked : Boolean)

    @Query("UPDATE NOTES_LIST_TABLE SET addedToCart= :addedToCart where _id = :id")
    fun updateAddToCartNotes(id: String, addedToCart : Boolean)
}