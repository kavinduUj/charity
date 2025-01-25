package com.group.charity.data.repository


import com.group.charity.data.dto.AttendEvent
import com.group.charity.data.dto.SignUpDto
import com.group.charity.data.dto.allEvent.EventListResponse
import com.group.charity.data.dto.eventDetails.EventDetailsResponse
import com.group.charity.data.remote.ApiService
import com.group.charity.domain.repository.EventRepo
import javax.inject.Inject

class EventImpl @Inject constructor(
    private val apiService: ApiService
) : EventRepo {
    override suspend fun getAll(tok: String): EventListResponse {
        return apiService.allEvent(tok,"1")
    }

    override suspend fun createEvent(tok: String, hashMap: HashMap<String, Any>): SignUpDto {
        return apiService.createEvent(
            tok,hashMap
        )
    }

    override suspend fun eventDetails(tok: String, id: String): EventDetailsResponse {
        return apiService.eventDetails(
            tok,id
        )
    }

    override suspend fun attendEvent(tok: String, id: String): AttendEvent {
        return apiService.attendEvent(
            tok,id
        )
    }

}