package com.example.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.notesapp.R
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

private val emailRegex = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

private val passwordRegex = Pattern.compile(
    "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
)

private val numberRegex = Pattern.compile(
    "\\d{10}"
)

fun String.isEmail() : Boolean {
    return emailRegex.matcher(this).matches()
}

fun showSnackBar(view: View, msg: String, color: Int, bgColor: Int) {
    val snackbar = Snackbar.make(
        view, msg,
        Snackbar.LENGTH_LONG
    )
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(bgColor)
    val textView =
        snackbarView.findViewById(R.id.snackbar_text) as TextView
    textView.setTextColor(color)
    textView.textSize = 14f
    snackbar.show()
}

fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    activity.currentFocus?.let { inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0) }
}

fun String.isValidPassword(): Boolean {
    return passwordRegex.matcher(this).matches()
}

fun String.isValidPhoneNumber(): Boolean {
    return numberRegex.matcher(this).matches()
}