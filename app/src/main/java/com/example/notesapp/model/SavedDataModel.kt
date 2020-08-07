package com.example.notesapp.model

import java.io.Serializable

data class SavedDataModel(
    var fullName: String? = "",
    var emailId: String? = "",
    var number: String? = "",
    var otp: Int? = 0,
    var studentId: Int? = 0,
    var techShortName: String? = "",
    var techFullName: String? = "",
    var color: Int? = 0
) : Serializable