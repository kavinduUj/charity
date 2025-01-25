package com.group.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.group.charity.R
import com.group.charity.databinding.ErrorAlertBinding

class ErrorAlert (
    private val title_: String,
    private val message_: String,
    private val action: (() -> Unit)? = null
) : DialogFragment() {

    private lateinit var binding: ErrorAlertBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ErrorAlertBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawableResource(android.R.color.transparent)
            attributes = WindowManager.LayoutParams().apply {
                copyFrom(dialog?.window?.attributes)
                width = (resources.displayMetrics.widthPixels * 0.92).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            attributes.windowAnimations = R.style.DialogAnimation
        }
    }

    private fun init() {
        binding.apply {
            close.setOnClickListener {
                dismissWithAnimation()
            }
            done.setOnClickListener {
                dismissWithAnimation()
                action?.let {
                    it()
                }
            }
            title.text = title_
            message.text = message_
        }
    }

    private fun dismissWithAnimation() {
        dismiss()
    }
}