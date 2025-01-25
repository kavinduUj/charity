package com.group.charity.presentation.fragment.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.group.charity.R
import com.group.charity.data.dto.allEvent.EventListResponseItem
import com.group.charity.databinding.EventDetailsBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.util.base64ToBitmap
import com.group.util.toFormattedDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetails(
    private val event: EventListResponseItem
) : Fragment() {

    private lateinit var binding: EventDetailsBinding
    lateinit var baseActivity: BaseActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EventDetailsBinding.inflate(layoutInflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        baseActivity = requireActivity() as BaseActivity
        binding.apply {
            goBack.setOnClickListener { parentFragmentManager.popBackStack() }
            Glide.with(requireContext())
                .load(if (event.images[0].contains("http"))event.images[0] else event.images[0].base64ToBitmap())
                .placeholder(R.drawable.event_cover)
                .error(R.drawable.event_cover)
                .centerCrop()
                .into(coverImg)
            userCount.text = "${event.attendUsers.size}"
            title.text = event.eventName
            location.text = event.location
            about.text = event.aboutEvent
            date.text = "${event.startDate.toFormattedDate()} - ${event.endDate.toFormattedDate()}"
        }
    }
}