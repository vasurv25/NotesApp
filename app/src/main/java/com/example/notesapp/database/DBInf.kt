package com.example.notesapp.database

import com.example.notesapp.model.NotesDataModel

interface DBInf : NotesAppDBInf

interface NotesAppDBInf {
    fun insertAllNotes(notesDataModel: NotesDataModel)
}