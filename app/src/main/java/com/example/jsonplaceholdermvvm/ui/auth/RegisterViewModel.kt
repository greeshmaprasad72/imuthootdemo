package com.example.jsonplaceholdermvvm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonplaceholdermvvm.data.api.Resource
import com.example.jsonplaceholdermvvm.data.api.response.RegisterResponse
import com.example.jsonplaceholdermvvm.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private var _registerResponse= MutableLiveData<Resource<ArrayList<RegisterResponse>>>()
    val registerResponse: LiveData<Resource<ArrayList<RegisterResponse>>> = _registerResponse

    fun register(mobileNumber : String, fullName: String, password: String){
        viewModelScope.launch {
            _registerResponse.value= Resource.Loading
            val result= repository.register(mobileNumber,fullName,password)
            _registerResponse.value=result
        }

    }
}