package com.group.charity.domain.repository

import com.group.charity.data.dto.LoginDto
import com.group.charity.data.dto.SignUpDto

interface AuthRepo {
    suspend fun userLogin(body: HashMap<String,String>) : LoginDto
    suspend fun signup(body: HashMap<String,String>) : SignUpDto
}