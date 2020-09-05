package com.example.notesapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.database.dbmodel.AddressDBModel

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(addressDBModel: AddressDBModel): Long

    @Transaction
    @Query("Select * from ADDRESS_TABLE")
    fun getAddress(): LiveData<List<AddressDBModel>>

    @Query("UPDATE ADDRESS_TABLE SET isSelected= :isSelected where _id = :id")
    suspend fun updateSelectedAddress(id: Int, isSelected : Boolean) : Int
}