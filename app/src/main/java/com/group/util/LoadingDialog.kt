package com.group.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.group.charity.R

class LoadingDialog(private val context: Context) {

    private lateinit var waiting: Dialog

    fun init() {
        waiting = Dialog(context)
        waiting.setContentView(R.layout.loading_view)

        waiting.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        waiting.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        waiting.setCancelable(false)
        waiting.setCanceledOnTouchOutside(false)
    }

    fun isVisible() {
        waiting.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun isGone() {
        waiting.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}