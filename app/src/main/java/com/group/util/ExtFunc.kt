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
import android.content.ContentResolver
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

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

fun Uri.toBase64(contentResolver: ContentResolver): String? {
    return try {
        // Open an InputStream from the Uri
        val inputStream: InputStream? = contentResolver.openInputStream(this)
        inputStream?.let {
            // Decode InputStream to Bitmap
            val bitmap = BitmapFactory.decodeStream(it)

            // Convert Bitmap to ByteArray
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream) // Adjust quality if needed
            val byteArray = byteArrayOutputStream.toByteArray()

            // Encode ByteArray to Base64
            android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null if conversion fails
    }
}
fun Uri.toCompressedBase64(contentResolver: ContentResolver, maxWidth: Int, maxHeight: Int, quality: Int): String? {
    return try {
        val inputStream = contentResolver.openInputStream(this)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        // Scale down the Bitmap
        val scaledBitmap = Bitmap.createScaledBitmap(
            originalBitmap,
            maxWidth,
            maxHeight,
            true
        )

        // Convert to ByteArray with reduced quality
        val byteArrayOutputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Encode ByteArray to Base64
        android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


