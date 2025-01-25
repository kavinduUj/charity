package com.group.charity.presentation.fragment.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.charity.data.dto.LoginDto
import com.group.charity.domain.useCase.auth.LoginUseCase
import com.group.util.CommonState
import com.group.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _loginStateFLow = MutableStateFlow<CommonState<LoginDto>>(CommonState.Loading)
    val loginStateFlow: StateFlow<CommonState<LoginDto>> = _loginStateFLow
    fun userLogin(body: HashMap<String,String>) {
        loginUseCase(body = body).onEach { result->
            when(result) {
                is Resource.Success -> {
                    _loginStateFLow.value = CommonState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _loginStateFLow.value = CommonState.Error(result.message ?: "unknown")
                }
                is Resource.Loading -> {
                    _loginStateFLow.value = CommonState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}