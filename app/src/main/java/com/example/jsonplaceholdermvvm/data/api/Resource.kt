package com.example.jsonplaceholdermvvm.data.api

// Generic wrapper the ViewModel exposes to the UI so it can react to
// loading / success / failure without leaking Retrofit types upward.
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorCode: Int,
        val errorMessage: String
    ) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Reset : Resource<Nothing>()
}
