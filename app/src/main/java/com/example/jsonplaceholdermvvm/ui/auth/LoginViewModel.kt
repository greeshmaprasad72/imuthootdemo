package com.example.jsonplaceholdermvvm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholdermvvm.data.api.Resource
import com.example.jsonplaceholdermvvm.data.api.response.CheckMobileNumberResponse
import com.example.jsonplaceholdermvvm.data.api.response.LoginResponse
import com.example.jsonplaceholdermvvm.data.local.TokenManager
import com.example.jsonplaceholdermvvm.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _checkMobileNumberResponse =
        MutableLiveData<Resource<ArrayList<CheckMobileNumberResponse>>>()
    val checkMobileNumberResponse:
            LiveData<Resource<ArrayList<CheckMobileNumberResponse>>> =
        _checkMobileNumberResponse

    private val _loginResponse= MutableLiveData<Resource<ArrayList<LoginResponse>>>()
    val loginResponse: LiveData<Resource<ArrayList<LoginResponse>>> = _loginResponse

     fun checkMobileNumber(mobileNumber: String){
         viewModelScope.launch {
             _checkMobileNumberResponse.value= Resource.Loading
             _checkMobileNumberResponse.value= authRepository.checkMobileNumber(mobileNumber)
         }

    }

    fun login(mobileNumber: String, password: String){
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
           val result = authRepository.login(mobileNumber,password)
            if(result is Resource.Success){
                val loginData=result.value.firstOrNull()
                if(loginData?.AuthToken!=null){
                    tokenManager.saveSession(
                       loginData.MobileNumber?: mobileNumber,
                        loginData.FullName?:"",
                        loginData.AuthToken!!
                    )

                }

            }
            _loginResponse.value=result
        }
    }
}