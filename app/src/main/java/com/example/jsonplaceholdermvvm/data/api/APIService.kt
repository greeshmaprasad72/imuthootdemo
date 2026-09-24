package com.example.jsonplaceholdermvvm.data.api

import com.example.jsonplaceholdermvvm.data.api.request.CheckMobileNumberRequest
import com.example.jsonplaceholdermvvm.data.api.request.GetAPPVersionAndroidRequest
import com.example.jsonplaceholdermvvm.data.api.request.LoginRequest
import com.example.jsonplaceholdermvvm.data.api.request.RegisterRequest
import com.example.jsonplaceholdermvvm.data.api.response.BaseOutput
import com.example.jsonplaceholdermvvm.data.api.response.CheckMobileNumberResponse
import com.example.jsonplaceholdermvvm.data.api.response.GetAPPVersionAndroidResponse
import com.example.jsonplaceholdermvvm.data.api.response.LoginResponse
import com.example.jsonplaceholdermvvm.data.api.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    companion object {
        private const val SAVE_URL_PATH_END = "Save.ashx/ProcessRequest"
        private const val RETRIEVE_URL_PATH_END = "Retrieve.ashx/ProcessRequest"
    }

    @POST(SAVE_URL_PATH_END)
    suspend fun getAppVersion(
        @Body request: GetAPPVersionAndroidRequest

    ): Response<BaseOutput<ArrayList<GetAPPVersionAndroidResponse>>>

    @POST(SAVE_URL_PATH_END)
    suspend fun checkMobileNumber(
        @Body request: CheckMobileNumberRequest
    ):Response<BaseOutput<ArrayList<CheckMobileNumberResponse>>>

    @POST(SAVE_URL_PATH_END)
    suspend fun register(
        @Body request: RegisterRequest
    ) : Response<BaseOutput<ArrayList<RegisterResponse>>>

    @POST(SAVE_URL_PATH_END)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<BaseOutput<ArrayList<LoginResponse>>>
}
