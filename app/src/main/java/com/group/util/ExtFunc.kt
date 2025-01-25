package com.group.util


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileOutputStream
import java.util.Base64
import java.io.File
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
fun String.base64ToBitmap(): Bitmap? {
    return try {
        // Decode the Base64 string into a byte array
        val decodedBytes = Base64.getDecoder().decode(this)

        // Convert the byte array to a Bitmap
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if conversion fails
    }
}