package com.example.notesapp.database.dbmodel

interface ModelCommunicator<out T : Any> {
    fun get(): T
}