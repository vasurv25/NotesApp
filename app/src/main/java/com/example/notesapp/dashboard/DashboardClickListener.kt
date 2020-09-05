package com.example.notesapp.dashboard

interface DashboardClickListener {
    fun bookmarkCheckBoxClicked(isCheck: Boolean, id: String)
    fun deleteBookMark(id: String, position: Int)
    fun chooseOptionListener(id: String, position: Int)
    fun checkAddressSelection(id: Int, isSelected: Boolean)
}