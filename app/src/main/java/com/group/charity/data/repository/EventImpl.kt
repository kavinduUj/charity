package com.group.charity.data.repository


import com.group.charity.data.dto.SignUpDto
import com.group.charity.data.dto.allEvent.EventListResponse
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

}