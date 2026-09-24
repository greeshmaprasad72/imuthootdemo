package com.example.jsonplaceholdermvvm.ui.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jsonplaceholdermvvm.databinding.ItemOnboardingSlideBinding

class OnboardingPageAdapter(private val slides: List<OnboardingSlide>) :
    RecyclerView.Adapter<OnboardingPageAdapter.SlideViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SlideViewHolder {
        val binding = ItemOnboardingSlideBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SlideViewHolder,
        position: Int
    ) {
       val slide=slides[position]
        holder.binding.tvSlideTitle.text=slide.title
        holder.binding.tvSlideDescription.text=slide.description
        Glide.with(holder.binding.ivSlideImage.context).
                load(slide.imageUrl)
            .centerCrop()
            .into(holder.binding.ivSlideImage)


    }

    override fun getItemCount(): Int {
        return slides.size
    }

    class SlideViewHolder( val binding: ItemOnboardingSlideBinding) :
        RecyclerView.ViewHolder(binding.root) {




    }
}