package com.example.notesapp.database

interface DBActivityListener {
    fun getBookMarkIdUpdate(ids : List<String>)
    fun insertAllNotesUpdate()
    fun getAddedToCartIdUpdate(ids : List<String>)
}