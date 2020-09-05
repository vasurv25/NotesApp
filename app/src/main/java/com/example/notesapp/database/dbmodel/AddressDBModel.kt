package com.example.notesapp.database.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.database.ADDRESS_TABLE

@Entity(tableName = ADDRESS_TABLE)
data class AddressDBModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,

    @ColumnInfo(name = "houseNumber")
    var houseNumber: String = "",

    @ColumnInfo(name = "addressLine1")
    var addressLine1: String = "",

    @ColumnInfo(name = "addressLine2")
    var addressLine2: String = "",

    @ColumnInfo(name = "landmark")
    var landmark: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    @ColumnInfo(name = "pincode")
    var pincode: String = "",

    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean = false
    )