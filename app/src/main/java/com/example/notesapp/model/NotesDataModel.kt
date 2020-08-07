package com.example.notesapp.model

import java.io.Serializable

data class NotesDataModel(
    var id: String = "",
    var subjectName: String = "",
    var author: String = "",
    var rating: String = "",
    var price: String = "",
    var bookMarked: Boolean = false,
    var addedToCart: Boolean = false
) : Serializable