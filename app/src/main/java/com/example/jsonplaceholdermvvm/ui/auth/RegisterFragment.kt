package com.example.jsonplaceholdermvvm.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.jsonplaceholdermvvm.R
import com.example.jsonplaceholdermvvm.data.api.Resource
import com.example.jsonplaceholdermvvm.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding?=null
    private val binding get()=_binding!!
    private val viewModel : RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mobileNumber= arguments?.getString("mobileNumber").orEmpty()
        binding.etMobile.setText(mobileNumber)
        binding.btnRegister.setOnClickListener {
            if(!validateInputs()) return@setOnClickListener

            binding.tvRegisterError.text=""
            val fullName= binding.etFullName.text.toString().trim()
            val password= binding.etPassword.text.toString().trim()
            viewModel.register(mobileNumber,fullName, password)


        }
        clearErrorOnTyping()
        observeRegister()

    }
    private fun setLoading(isLoading: Boolean) {
        binding.loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun observeRegister(){
        viewModel.registerResponse.observe(viewLifecycleOwner){
            response->
            when(response){
                is Resource.Loading->{
                    setLoading(true)

                }
                is Resource.Success->{
                    setLoading(false)

                }

               is Resource.Failure->{
                   setLoading(false)
                   binding.tvRegisterError.text=response.errorMessage

               }
                is Resource.Reset-> Unit
            }
        }
    }

    private fun clearErrorOnTyping(){
        binding.etFullName.addTextChangedListener {
            binding.tilFullName.error=null
        }
        binding.etPassword.addTextChangedListener {
            binding.tilPassword.error=null
        }
        binding.etConfirmPassword.addTextChangedListener {
            binding.tilConfirmPassword.error=null
        }
    }

    private fun validateInputs() : Boolean{
        var valid=true
        val fullName=binding.etFullName.text.toString().trim()
        if(fullName.isBlank()){
            binding.tilFullName.error="Enter your full name"
            valid=false

        }else{
            binding.tilFullName.error=null
        }

        val password=binding.etPassword.text.toString().trim()
        if(password.isBlank()){
            binding.tilPassword.error="Enter Password"
            valid=false
        }else if(password.length<6){
            binding.tilPassword.error="Password must be at least 6 characters long"
            valid=false
        }
        else{
            binding.tilPassword.error=null
        }

        val confirmPassword= binding.etConfirmPassword.text.toString().trim()
        if(confirmPassword.isBlank()){
            binding.tilConfirmPassword.error="Enter Confirm Password"
            valid=false
        }else if(confirmPassword!=password){
            binding.tilConfirmPassword.error="Password does not match"
            valid=false
        }else{
            binding.tilConfirmPassword.error=null
        }
        return valid
    }


}