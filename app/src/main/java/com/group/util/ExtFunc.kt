package com.group.util

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.group.charity.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun FragmentManager.replaceFragment(
    fragment: Fragment,
    containerId: Int,
    addToBackStack: Boolean = true,
    backStackName: String? = null
) {
    val transaction = this.beginTransaction()
    transaction.replace(containerId, fragment)
    if (addToBackStack) {
        transaction.addToBackStack(backStackName)
    }
    transaction.commit()
}

fun FragmentManager.apiError (
    title: String = "Error",
    message: String = "",
    action: (() -> Unit)? = null
) {
    val apiErrorAlert = ErrorAlert(
        title,message, action
    )
    apiErrorAlert.show(this, "")
}

fun String.toFormattedDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date: Date = inputFormat.parse(this) ?: return this
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}