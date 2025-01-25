package com.group.charity.domain.useCase.auth

import com.group.charity.data.dto.LoginDto
import com.group.charity.data.dto.SignUpDto
import com.group.charity.domain.repository.AuthRepo
import com.group.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    operator fun invoke(body: HashMap<String, String>): Flow<Resource<SignUpDto>> = flow {
        try {
            emit(Resource.Loading())
            val loginData = authRepo.signup(body)
            emit(Resource.Success(loginData))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = errorBody?.let {
                try {
                    JSONObject(it).getString("message")
                } catch (jsonException: JSONException) {
                    e.message()
                }
            } ?: "An error occurred"
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "internet!!"))
        }
    }
}