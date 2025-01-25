package com.group.charity.di

import com.group.charity.data.remote.ApiService
import com.group.charity.data.repository.AuthImpl
import com.group.charity.data.repository.EventImpl
import com.group.charity.domain.repository.AuthRepo
import com.group.charity.domain.repository.EventRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoProvider {

    @Provides
    @Singleton
    fun authentication(apiService: ApiService) : AuthRepo {
        return AuthImpl(apiService)
    }

    @Provides
    @Singleton
    fun events(apiService: ApiService) : EventRepo {
        return EventImpl(apiService)
    }
}