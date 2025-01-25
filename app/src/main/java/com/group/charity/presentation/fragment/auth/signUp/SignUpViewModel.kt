package com.group.charity.presentation.fragment.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.charity.data.dto.LoginDto
import com.group.charity.data.dto.SignUpDto
import com.group.charity.domain.useCase.auth.SignUpUseCase
import com.group.util.CommonState
import com.group.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {
    private val _signupStateFLow = MutableStateFlow<CommonState<SignUpDto>>(CommonState.Loading)
    val signupStateFLow: StateFlow<CommonState<SignUpDto>> = _signupStateFLow
    fun userSignup(body: HashMap<String,String>) {
        signUpUseCase(body = body).onEach { result->
            when(result) {
                is Resource.Success -> {
                    _signupStateFLow.value = CommonState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _signupStateFLow.value = CommonState.Error(result.message ?: "unknown")
                }
                is Resource.Loading -> {
                    _signupStateFLow.value = CommonState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}