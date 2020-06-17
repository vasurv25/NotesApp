package com.example.utils

import android.app.Activity
import android.content.res.Resources
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

fun showSnackBar(view: View, msg: String, color: Int, resources: Resources) {
    val snackbar = Snackbar.make(
        view, msg,
        5000
    )
    val snackbarView = snackbar.view
    snackbarView.background = resources.getDrawable(R.drawable.snackbar_round_corner, null)
    val textView =
        snackbarView.findViewById(R.id.snackbar_text) as TextView
    textView.setTextColor(color)
    textView.textSize = 14f
    // Set an action on it, and a handler
    snackbar.setAction("DISMISS") { snackbar.dismiss() }
    snackbar.show()
}

fun validatePassword(password: String?): Boolean {
    var isValid = !password.isNullOrEmpty()
    if (isValid) {
        isValid = password!!.length >= 8
    }
    return isValid
}

fun validatePhoneNumber(phoneNumber: String?): Boolean {
    var isValid = !phoneNumber.isNullOrEmpty()
    if (isValid) {
        isValid = phoneNumber!!.length == 10
    }
    return isValid
}

/*private fun setSnackBar() {
    var snackbar = Snackbar.make(layout_login_parent, message, duration)
    View snackbarLayout = snackbar . getView ();
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    );
// Layout must match parent layout type
    lp.setMargins(50, 0, 0, 0);
// Margins relative to the parent view.
// This would be 50 from the top left.
    snackbarLayout.setLayoutParams(lp);
    snackbar.show();
}*/

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