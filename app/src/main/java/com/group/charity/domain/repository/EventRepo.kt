package com.group.charity.domain.repository

import com.group.charity.data.dto.allEvent.EventListResponse


interface EventRepo {
    suspend fun getAll(tok: String) : EventListResponse
}