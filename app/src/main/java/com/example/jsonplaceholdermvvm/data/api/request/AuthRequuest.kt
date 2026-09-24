package com.example.jsonplaceholdermvvm.data.api.request

import com.example.jsonplaceholdermvvm.data.AppConstants

data class CheckMobileNumberRequest(
    val MobileNumber: String,
    val MethodName: String = AppConstants.CHECK_MOBILE_NUMBER
)


data class LoginRequest(
    val MobileNumber: String,
    val Password: String,
    val MethodName: String
)

data class RegisterRequest(
    val MobileNumber: String,
    val Password: String,
    val FullName: String,
    val MethodName: String
)