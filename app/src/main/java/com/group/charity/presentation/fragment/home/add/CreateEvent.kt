package com.group.charity.presentation.fragment.home.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.group.charity.databinding.CreateEventBinding

class CreateEvent:Fragment() {

    lateinit var binding: CreateEventBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateEventBinding.inflate(layoutInflater, container,false)
        init()
        return binding.root
    }
    private fun init() {
        
    }
}