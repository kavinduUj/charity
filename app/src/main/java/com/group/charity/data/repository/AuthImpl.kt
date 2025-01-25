package com.group.charity.data.repository

import com.group.charity.data.dto.LoginDto
import com.group.charity.data.dto.SignUpDto
import com.group.charity.data.remote.ApiService
import com.group.charity.domain.repository.AuthRepo
import dagger.Provides
import javax.inject.Inject

class AuthImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepo {
    override suspend fun userLogin(body: HashMap<String, String>): LoginDto {
        return apiService.userLogin(
            body = body
        )
    }

    override suspend fun signup(body: HashMap<String, String>): SignUpDto {
        return apiService.userSignUp(
            body = body
        )
    }
}