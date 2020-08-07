package com.example.notesapp.dashboard

interface DashboardClickListener {
    fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String)
    fun deleteBookMark(id: String)
}