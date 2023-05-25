package com.carefer.core.utils

import android.os.Build

object VersionUtils {


    val isOreoAndLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}
