package com.group.charity.data.remote

import com.group.charity.data.dto.LoginDto
import com.group.util.EndPoints
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(EndPoints.LOGIN)
    suspend fun userLogin(
        @Body body: HashMap<String,String>
    ) : LoginDto
}