package com.group.charity.presentation.fragment.home.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.charity.data.dto.SignUpDto
import com.group.charity.domain.useCase.event.CreateEventUseCase
import com.group.util.CommonState
import com.group.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val createEventUseCase: CreateEventUseCase
): ViewModel() {
    private val _createEventStateFLow = MutableStateFlow<CommonState<SignUpDto>>(CommonState.Loading)
    val createEventStateFLow: StateFlow<CommonState<SignUpDto>> = _createEventStateFLow
    fun createEvent(tok: String, body: HashMap<String,Any>) {
        createEventUseCase(
            tok = tok, body = body).onEach { result->
            when(result) {
                is Resource.Success -> {
                    _createEventStateFLow.value = CommonState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _createEventStateFLow.value = CommonState.Error(result.message ?: "unknown")
                }
                is Resource.Loading -> {
                    _createEventStateFLow.value = CommonState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}