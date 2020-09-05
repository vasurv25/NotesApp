package com.example.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.io.Serializable

data class AddressDataModel(
    var id: Int = 0,
    var houseNumber: String = "",
    var addressLine1: String = "",
    var addressLine2: String = "",
    var landmark: String = "",
    var city: String = "",
    var pincode: String = "",
    var isSelected: Boolean = false
) : Serializable

