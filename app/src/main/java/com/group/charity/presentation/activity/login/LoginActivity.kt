package com.group.charity.presentation.activity.login

import android.os.Bundle
import com.group.charity.R
import com.group.charity.databinding.ActivityLoginBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.charity.presentation.fragment.auth.login.LoginFragment
import com.group.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.replaceFragment(
            LoginFragment(),
            R.id.loginFrame
        )
    }
}