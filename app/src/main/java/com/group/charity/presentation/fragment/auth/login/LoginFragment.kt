package com.group.charity.presentation.fragment.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.group.charity.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.group.charity.presentation.activity.BaseActivity
import com.group.util.CommonState
import com.group.util.apiError
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var baseActivity: BaseActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(layoutInflater, container,false)
        init()
        return binding.root
    }

    private fun init() {
        baseActivity = requireActivity() as BaseActivity
        binding.apply {
            loginBtn.setOnClickListener {
                userLogin()
            }
        }
    }

    private fun userLogin() {
        val hashMap: HashMap<String,String> = HashMap()
        hashMap["email"] = "abc@gmail.com"
        hashMap["password"] = "Test123@"

        loginViewModel.userLogin(hashMap)
        lifecycleScope.launch {
            loginViewModel.loginStateFlow.collect { result->
                when(result) {
                    is CommonState.Loading -> {
                        Log.i("loginData","isLoading")
                        baseActivity.loading.isVisible()
                    }
                    is CommonState.Success -> {
                        baseActivity.loading.isGone()
                        Log.i("loginData", "data: ${Gson().toJson(result.data)}")
                        cancel()
                    }
                    is CommonState.Error -> {
                        baseActivity.loading.isGone()
                        Log.i("loginData", "data: ${result.message}")
                    }
                }
            }
        }
    }
}