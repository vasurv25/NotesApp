package com.example.notesapp.database.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.database.NOTES_LIST_TABLE

@Entity(tableName = NOTES_LIST_TABLE)
data class NotesDBModel(
    @ColumnInfo(name = "_id")
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "subjectName")
    val subjectName: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "rating")
    val rating: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "bookMarked")
    val bookMarked: Boolean,

    @ColumnInfo(name = "addedToCart")
    val addedToCart: Boolean
)