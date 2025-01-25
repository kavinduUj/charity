package com.group.util

import android.util.Log

fun logOther(message: String) {
    Log.i("charityLog", "$message\n\n")
}
fun logErr(message: String) {
    Log.e("charityLog", "$message\n")
}