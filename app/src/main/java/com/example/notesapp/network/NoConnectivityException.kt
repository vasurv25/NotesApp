package com.example.notesapp.network

import android.content.Context
import com.example.notesapp.R
import java.io.IOException

class NoConnectivityException(val context: Context) : IOException() {
    override fun getLocalizedMessage(): String {
        return context.getString(R.string.no_network_connectivity)
    }
}