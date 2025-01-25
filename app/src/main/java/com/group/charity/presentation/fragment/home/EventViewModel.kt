package com.group.charity.presentation.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.charity.data.dto.allEvent.EventListResponse
import com.group.charity.domain.useCase.event.AllEventUseCase
import com.group.util.CommonState
import com.group.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val allEventUseCase: AllEventUseCase
): ViewModel() {
    private val _eventStateFLow = MutableStateFlow<CommonState<EventListResponse>>(CommonState.Loading)
    val eventStateFLow: StateFlow<CommonState<EventListResponse>> = _eventStateFLow
    fun allEvents(tok: String) {
        allEventUseCase(tok).onEach { result->
            when(result) {
                is Resource.Success -> {
                    _eventStateFLow.value = CommonState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _eventStateFLow.value = CommonState.Error(result.message ?: "unknown")
                }
                is Resource.Loading -> {
                    _eventStateFLow.value = CommonState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}