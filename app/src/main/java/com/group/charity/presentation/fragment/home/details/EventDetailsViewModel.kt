package com.group.charity.presentation.fragment.home.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group.charity.data.dto.eventDetails.EventDetailsResponse
import com.group.charity.domain.useCase.event.EventDetailsUseCase
import com.group.util.CommonState
import com.group.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val eventDetailsUseC: EventDetailsUseCase
): ViewModel() {
    private val _createEventStateFLow = MutableStateFlow<CommonState<EventDetailsResponse>>(CommonState.Loading)
    val createEventStateFLow: StateFlow<CommonState<EventDetailsResponse>> = _createEventStateFLow
    fun createEvent(tok: String, body: String) {
        eventDetailsUseC(
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