package com.group.charity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.group.charity.databinding.ActivityMainBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.charity.presentation.activity.home.HomeActivity
import com.group.charity.presentation.activity.login.LoginActivity
import com.group.charity.presentation.fragment.auth.login.LoginFragment
import com.group.charity.presentation.fragment.auth.login.LoginViewModel
import com.group.util.CommonState
import com.group.util.PrefData
import com.group.util.apiError
import com.group.util.replaceFragment
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Prefs.getString(PrefData.USER_TOKEN).isNotEmpty()) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.getStart.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
}