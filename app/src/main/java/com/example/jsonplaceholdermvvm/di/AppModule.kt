package com.example.jsonplaceholdermvvm.di

import com.example.jsonplaceholdermvvm.data.api.APIService
import com.example.jsonplaceholdermvvm.data.api.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Tells Hilt HOW to build an APIService whenever something asks for one
// (e.g. UserRepository's constructor). Lives for the whole app (SingletonComponent).
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(remoteDataSource: RemoteDataSource): APIService {
        return remoteDataSource.buildApi(APIService::class.java)
    }
}
