package com.example.jsonplaceholdermvvm.data.repository

import android.R
import com.example.jsonplaceholdermvvm.data.AppConstants
import com.example.jsonplaceholdermvvm.data.api.APIService
import com.example.jsonplaceholdermvvm.data.api.Resource
import com.example.jsonplaceholdermvvm.data.api.SafeAPICall
import com.example.jsonplaceholdermvvm.data.api.request.CheckMobileNumberRequest
import com.example.jsonplaceholdermvvm.data.api.request.LoginRequest
import com.example.jsonplaceholdermvvm.data.api.request.RegisterRequest
import com.example.jsonplaceholdermvvm.data.api.response.CheckMobileNumberResponse
import com.example.jsonplaceholdermvvm.data.api.response.LoginResponse
import com.example.jsonplaceholdermvvm.data.api.response.RegisterResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: APIService
) :
SafeAPICall{
    suspend fun checkMobileNumber(mobileNumber: String): Resource<ArrayList<CheckMobileNumberResponse>> {
        val request = CheckMobileNumberRequest(mobileNumber, AppConstants.CHECK_MOBILE_NUMBER)
        return safeApiCall { apiService.checkMobileNumber(request) }
    }
    suspend fun register(
        mobileNumber: String,
        fullName: String,
        password: String
    ): Resource<ArrayList<RegisterResponse>>{
        val request = RegisterRequest(
            mobileNumber,
            password,
            fullName,
            AppConstants.REGISTER,
            )
        return safeApiCall{apiService.register(request)}
    }

    suspend fun login(
        mobileNumber: String,
        password: String
    ): Resource<ArrayList<LoginResponse>>{
        val request = LoginRequest(
            mobileNumber,
            password,
            AppConstants.LOGIN
        )
        return safeApiCall { apiService.login(request) }

    }

}