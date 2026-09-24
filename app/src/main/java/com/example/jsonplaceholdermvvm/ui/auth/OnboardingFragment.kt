package com.example.jsonplaceholdermvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.jsonplaceholdermvvm.R
import com.example.jsonplaceholdermvvm.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding  get()= _binding!!



    // Placeholder copy — swap these for your own generic feature descriptions.
    private val slides = listOf(
        OnboardingSlide(
            title = "Apply for loans without leaving home",
            description = "Gold, personal, business, and home loans — all just a few taps away.",
            imageUrl = "https://images.pexels.com/photos/34518920/pexels-photo-34518920.jpeg"
        ),
        OnboardingSlide(
            title = "Repay from anywhere",
            description = "Track and repay your loans from wherever you are.",
            imageUrl = "https://images.pexels.com/photos/6969802/pexels-photo-6969802.jpeg"
        ),
        OnboardingSlide(
            title = "Bank-grade security",
            description = "Biometric login and secure storage keep your account protected.",
            imageUrl = "https://images.pexels.com/photos/5882533/pexels-photo-5882533.jpeg"
        )
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding= FragmentOnboardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpOnboarding.adapter= OnboardingPageAdapter(slides)
        setupDots()
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)

        }

    }

    private fun setupDots(){
        val dots= mutableListOf<View>()
        binding.llDots.removeAllViews()

        for (i in slides.indices){
            val dot = View(requireContext()).apply {
                layoutParams= ViewGroup.MarginLayoutParams(20,20).apply {
                    setMargins(8,0,8,0)
                }

                setBackgroundResource(
                  if( i==0) R.drawable.dot_active else R.drawable.dot_inactive
                )
            }
            dots.add(dot)
            binding.llDots.addView(dot)
        }

        binding.vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
               dots.forEachIndexed { index, view ->
                   view.setBackgroundResource(
                       if(index==position) R.drawable.dot_active else R.drawable.dot_inactive


                   )
               }
            }
        })



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}