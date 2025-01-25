package com.group.charity.domain.repository

import com.group.charity.data.dto.LoginDto

interface AuthRepo {
    suspend fun userLogin(body: HashMap<String,String>) : LoginDto
}