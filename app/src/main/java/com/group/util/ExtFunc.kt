package com.group.util

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.group.charity.R

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