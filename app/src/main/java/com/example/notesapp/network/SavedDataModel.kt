package com.example.notesapp.network

import java.io.Serializable

data class SavedDataModel (
    var fullName: String? = "",
    var emailId: String? = "",
    var number: String? = "",
    var password : String? = "",
    var otp: String? = ""
) : Serializable