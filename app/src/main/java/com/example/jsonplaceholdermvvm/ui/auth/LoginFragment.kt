package com.example.jsonplaceholdermvvm.ui.auth

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jsonplaceholdermvvm.R
import com.example.jsonplaceholdermvvm.data.api.Resource
import com.example.jsonplaceholdermvvm.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding?=null
    private val binding get()=_binding!!
    var mobileNumberConfirmed=false
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickableItems()
        clearErrorOnTyping()
        binding.btnContinue.setOnClickListener {
            if (!validateInputs()) return@setOnClickListener
            binding.tvLoginError.text=""
            val mobileNumber=binding.etMobile.text.toString().trim()
            if(!mobileNumberConfirmed){
                viewModel.checkMobileNumber(mobileNumber)

            }else{
                val password=binding.etPassword.text.toString().trim()
                viewModel.login(mobileNumber,password)

            }


        }
        observeCheckMobileNumber()
        observeLogin()
    }

    private fun observeLogin(){
        viewModel.loginResponse.observe(viewLifecycleOwner){
            response->
            when(response){
                is Resource.Loading-> setLoading(true)
                is Resource.Success->{
                    setLoading(false)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                }
                is Resource.Failure->{
                    setLoading(false)
                    binding.tvLoginError.text=response.errorMessage
                }
                is Resource.Reset-> Unit
            }
        }
    }


    private fun observeCheckMobileNumber(){
        viewModel.checkMobileNumberResponse.observe(viewLifecycleOwner){
            response->
                when(response){
                    is Resource.Loading-> setLoading(true)
                    is Resource.Success->{
                        setLoading(false)
                        val exists= response.value.firstOrNull()?.Exists?:false
                        if(exists){
                            mobileNumberConfirmed=true
                            binding.tilPassword.visibility= View.VISIBLE
                            binding.btnContinue.text="Sign in"
                        }else{
                            val bundle= Bundle().apply {
                                putString("mobileNumber", binding.etMobile.text.toString())
                            }
                            findNavController().
                            navigate(R.id.action_loginFragment_to_registerFragment,bundle)
                        }

                    }
                    is Resource.Failure-> {
                        setLoading(false)
                        binding.tvLoginError.text=response.errorMessage
                    }
                    is Resource.Reset-> Unit

                    else -> {}
                }


        }
    }

    private fun setupClickableItems(){
        val fullText = "By continuing, I agree to the Terms and Conditions"
        val spannableString= SpannableString(fullText)
        val linkStart=fullText.indexOf(fullText)
        val linkEnd=linkStart+"Terms and conditions".length
        spannableString.setSpan( object: ClickableSpan(){
            override fun onClick(p0: View) {
                Toast.makeText(context, "Terms and conditions", Toast.LENGTH_SHORT).show()
            }
        },linkStart,linkEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

        )
        binding.tvTerms.text=spannableString
        binding.tvTerms.movementMethod= LinkMovementMethod.getInstance()

    }

    private fun clearErrorOnTyping(){
        binding.etMobile.addTextChangedListener {
            binding.tilMobile.error=""
        }
        binding.etPassword.addTextChangedListener {
            binding.tilPassword.error=""
        }
    }

    fun validateInputs() : Boolean{
        var isValid=true
        var mobileNumber=binding.etMobile.text.toString().trim()
        if (mobileNumber.length!=10){
            binding.tilMobile.error="Enter a 10 digit mobile number"
            isValid=false

        }else{
            binding.tilMobile.error=""
            isValid=true

        }
        return isValid

    }
    private fun setLoading(isLoading: Boolean) {
        binding.loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}