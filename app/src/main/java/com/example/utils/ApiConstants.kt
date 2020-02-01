package com.example.utils

import androidx.annotation.IntDef

class AuthTypes {
    @IntDef(SOCIAL_MEDIA, MOBILE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class AuthType

    companion object {
        const val SOCIAL_MEDIA = 1
        const val MOBILE = 3
    }
}