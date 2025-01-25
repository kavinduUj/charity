package com.group.charity.presentation.fragment.auth.signUp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.group.charity.databinding.SignupFragmentBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.charity.presentation.activity.login.LoginActivity
import com.group.util.CommonState
import com.group.util.apiError
import com.group.util.logErr
import com.group.util.logOther
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: SignupFragmentBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var baseActivity: BaseActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignupFragmentBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        baseActivity = requireActivity() as BaseActivity
        binding.apply {
            gotoLogin.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            signUpBtn.setOnClickListener {
                if (userValidate()) {
                    userSignUp()
                }
            }
        }
    }

    private fun userSignUp() {
        val hashMap: HashMap<String,String> = HashMap()
        hashMap["fullName"] = binding.fullName.text.toString()
        hashMap["email"] = binding.maiAdd.text.toString()
        hashMap["password"] = binding.password.text.toString()

        signUpViewModel.userSignup(hashMap)
        lifecycleScope.launch {
            signUpViewModel.signupStateFLow.collect { result->
                when(result) {
                    is CommonState.Loading -> {
                        baseActivity.loading.isVisible()
                        logOther("userSignUp req: ${Gson().toJson(hashMap)}")
                    }
                    is CommonState.Success -> {
                        baseActivity.loading.isGone()
                        logOther("userSignUp req: ${Gson().toJson(result.data)}")
                        parentFragmentManager.apiError(
                            title = "Success",
                            message = "User registered successfully",
                            action = {
                                requireActivity().finish()
                                startActivity(Intent(requireContext(), LoginActivity::class.java))
                            }
                        )
                        cancel()
                    }
                    is CommonState.Error -> {
                        baseActivity.loading.isGone()
                        cancel()
                        parentFragmentManager.apiError(
                            message = result.message
                        )
                        logErr("userSignUp err: ${Gson().toJson(result.message)}")
                    }
                }
            }
        }
    }
    private fun userValidate(): Boolean {
        if (binding.fullName.text.toString()
                .isEmpty()
        ) parentFragmentManager.apiError(message = "User name cannot be empty!")
            .also { return false }
        if (binding.maiAdd.text.toString()
                .isEmpty()
        ) parentFragmentManager.apiError(message = "Email cannot be empty!").also { return false }
        if (binding.password.text.toString()
                .isEmpty()
        ) parentFragmentManager.apiError(message = "Password cannot be empty!")
            .also { return false }
        if (binding.confirmPass.text.toString()
                .isEmpty()
        ) parentFragmentManager.apiError(message = "Confirm Password cannot be empty!")
            .also { return false }
        if (binding.password.text.toString() != binding.confirmPass.text.toString()) parentFragmentManager.apiError(message = "Password doesn't match")
            .also { return false }

        return true
    }
}