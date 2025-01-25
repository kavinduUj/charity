package com.group.charity.data.remote

import com.group.charity.data.dto.LoginDto
import com.group.charity.data.dto.SignUpDto
import com.group.charity.data.dto.allEvent.EventListResponse
import com.group.util.EndPoints
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST(EndPoints.LOGIN)
    suspend fun userLogin(
        @Body body: HashMap<String,String>
    ) : LoginDto

    @POST(EndPoints.SIGN_UP)
    suspend fun userSignUp(
        @Body body: HashMap<String,String>
    ) : SignUpDto

    @GET(EndPoints.ALL_EVENT)
    suspend fun allEvent(
        @Header("Authorization") authHeader: String,
        @Path("status") path: String
    ): EventListResponse
}