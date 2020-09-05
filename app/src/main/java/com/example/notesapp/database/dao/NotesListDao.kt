package com.example.notesapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.database.dbmodel.NotesDBModel

@Dao
interface NotesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotes(notesDBModel: NotesDBModel): Long

    @Transaction
    @Query("Select * from NOTES_LIST_TABLE")
    fun getAllNotes(): LiveData<List<NotesDBModel>>

    @Transaction
    @Query("Select * from NOTES_LIST_TABLE where bookMarked = :isCheck")
    fun getBookMarkNotes(isCheck: Boolean): LiveData<List<NotesDBModel>>

    @Transaction
    @Query("Select _id from NOTES_LIST_TABLE where bookMarked = :isCheck")
    suspend fun getBookMarkIds(isCheck: Boolean): List<String>

    @Transaction
    @Query("Select _id from NOTES_LIST_TABLE where addedToCart = :isCheck")
    suspend fun getAddedToCartIds(isCheck: Boolean): List<String>

    @Transaction
    @Query("Select * from NOTES_LIST_TABLE where addedToCart = :isCheck")
    fun getAddedToCartNotes(isCheck: Boolean): LiveData<List<NotesDBModel>>

    @Query("DELETE FROM NOTES_LIST_TABLE")
    suspend fun deleteAllNotes() : Int

    @Query("UPDATE NOTES_LIST_TABLE SET bookMarked= :bookMarked where _id = :id")
    suspend fun updateBookMarkNotes(id: String, bookMarked : Boolean) : Int

    @Query("UPDATE NOTES_LIST_TABLE SET addedToCart= :addedToCart where _id = :id")
    suspend fun updateAddToCartNotes(id: String, addedToCart : Boolean) : Int
}