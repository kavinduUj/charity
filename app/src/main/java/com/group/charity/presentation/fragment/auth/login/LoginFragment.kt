package com.group.charity.presentation.fragment.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.group.charity.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.group.charity.R
import com.group.charity.presentation.activity.BaseActivity
import com.group.charity.presentation.activity.home.HomeActivity
import com.group.charity.presentation.fragment.auth.signUp.SignUpFragment
import com.group.util.CommonState
import com.group.util.PrefData
import com.group.util.apiError
import com.group.util.logErr
import com.group.util.logOther
import com.group.util.replaceFragment
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

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
            signUp.setOnClickListener {
                parentFragmentManager.replaceFragment(
                    SignUpFragment(),
                    R.id.loginFrame
                )
            }
        }
    }
    private fun userLogin() {
        val hashMap: HashMap<String,String> = HashMap()
        hashMap["email"] = binding.email.text.toString()
        hashMap["password"] = binding.password.text.toString()

        loginViewModel.userLogin(hashMap)
        lifecycleScope.launch {
            loginViewModel.loginStateFlow.collect { result->
                when(result) {
                    is CommonState.Loading -> {
                        logOther("userLogin req: ${Gson().toJson(hashMap)}")
                        baseActivity.loading.isVisible()
                    }
                    is CommonState.Success -> {
                        baseActivity.loading.isGone()
                        logOther("userLogin res: ${Gson().toJson(result.data)}")
                        Prefs.putString(PrefData.USER_TOKEN, result.data.token)
                        Prefs.putString(PrefData.USER_ID, result.data.userId)
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                        cancel()
                    }
                    is CommonState.Error -> {
                        baseActivity.loading.isGone()
                        parentFragmentManager.apiError(
                            message = result.message
                        )
                        cancel()
                        logErr("userLogin req: ${Gson().toJson(result.message)}")
                    }
                }
            }
        }
    }
}