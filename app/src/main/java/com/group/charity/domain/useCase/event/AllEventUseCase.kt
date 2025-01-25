package com.group.charity.domain.useCase.event

import com.group.charity.data.dto.allEvent.EventListResponse
import com.group.charity.domain.repository.EventRepo
import com.group.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AllEventUseCase @Inject constructor(
    private val eventRepo: EventRepo
) {
    operator fun invoke(tok:String): Flow<Resource<EventListResponse>> = flow {
        try {
            emit(Resource.Loading())
            val loginData = eventRepo.getAll(tok)
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