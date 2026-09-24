package com.example.jsonplaceholdermvvm.data.api.response

data class CheckMobileNumberResponse(
    var MobileNumber: String? = null,
    var Exists: Boolean? = null
)

data class LoginResponse(
    var MobileNumber: String? = null,
    var FullName: String? = null,
    var AuthToken: String? = null
)

data class RegisterResponse(
    var MobileNumber: String? = null,
    var FullName: String? = null,
    var AuthToken: String? = null
)