package com.example.jsonplaceholdermvvm.data.api

import com.example.jsonplaceholdermvvm.data.api.response.BaseOutput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException

interface SafeAPICall {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<BaseOutput<T>>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val apiResponse = response.body()
                        var errorCode = apiResponse!!.Response!![0].ERROR_CODE
                        if (errorCode.isNullOrEmpty()) {
                            errorCode = "101"
                        }
                        var errorMessage = apiResponse.Response!![0].ERROR_MSG
                        if (errorMessage.isNullOrEmpty()) {
                            errorMessage = ""
                        }
                        if (errorCode == "404") {
                            if (apiResponse.ResultSet != null) {
                                Resource.Success(apiResponse.ResultSet!!)
                            } else {
                                Resource.Failure(errorCode.toInt(), errorMessage)
                            }
                        } else {
                            Resource.Failure(errorCode.toInt(), errorMessage)
                        }
                    } else {
                        Resource.Failure(101, "Something went wrong. Please try again later.")
                    }
                } else {
                    Resource.Failure(101, "Something went wrong. Please try again later.")
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is ConnectException -> {
                        Resource.Failure(101, "Something went wrong. Please try again later.")
                    }
                    else -> {
                        Resource.Failure(101, "Something went wrong. Please try again later.")
                    }
                }
            }
        }
    }
}