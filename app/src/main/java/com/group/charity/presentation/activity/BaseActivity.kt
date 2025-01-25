package com.group.charity.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.group.util.LoadingDialog

abstract class BaseActivity: AppCompatActivity() {
    lateinit var loading: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        loading = LoadingDialog(this)
        loading.init()
    }
}