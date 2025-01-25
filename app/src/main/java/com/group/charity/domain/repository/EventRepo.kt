package com.group.charity.domain.repository

import com.group.charity.data.dto.SignUpDto
import com.group.charity.data.dto.allEvent.EventListResponse


interface EventRepo {
    suspend fun getAll(tok: String) : EventListResponse
    suspend fun createEvent(tok: String, hashMap: HashMap<String,Any>) : SignUpDto
}