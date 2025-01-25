package com.group.charity.presentation.fragment.home.details

import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.group.charity.R
import com.group.charity.data.dto.allEvent.EventListResponseItem
import com.group.charity.databinding.EventDetailsBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.charity.presentation.fragment.home.AttendEventViewModel
import com.group.util.CommonState
import com.group.util.PrefData
import com.group.util.apiError
import com.group.util.base64ToBitmap
import com.group.util.logOther
import com.group.util.toFormattedDate
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventDetails(
    private val event: EventListResponseItem
) : Fragment() {

    private lateinit var binding: EventDetailsBinding
    lateinit var baseActivity: BaseActivity
    private val attendEventViewModel by viewModels<AttendEventViewModel>()

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

            iamIn.setOnClickListener {
                attend(event._id)
            }
            if(event.attendUsers.contains(Prefs.getString(PrefData.USER_ID))) {
                binding.inText.setBackgroundResource(R.drawable.green_filled)
                binding.inText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.inText.compoundDrawablesRelative.forEach { drawable ->
                    drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
                }
            } else {
                binding.inText.setBackgroundResource(R.drawable.custom_edit_text)
                binding.inText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding.inText.compoundDrawablesRelative.forEach { drawable ->
                    drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        }
    }

    private fun attend(id: String) {
        attendEventViewModel.createEvent(
            "Bearer ${Prefs.getString(PrefData.USER_TOKEN)}",
            id
        )

        lifecycleScope.launch {
            attendEventViewModel.createEventStateFLow.collect { res->
                when(res) {
                    is CommonState.Loading -> {
                        baseActivity.loading.isVisible()
                    }
                    is CommonState.Success -> {
                        baseActivity.loading.isGone()
                        logOther("attendToEvent: ${Gson().toJson(res.data)}")
                        if(res.data.message == "User added to event") {
                            binding.iamIn.setBackgroundResource(R.drawable.green_filled)
                            binding.inText.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                            binding.inText.compoundDrawablesRelative.forEach { drawable ->
                                drawable?.setTint(ContextCompat.getColor(requireContext(), android.R.color.white))
                            }
                        } else {
                            binding.iamIn.setBackgroundResource(R.drawable.custom_edit_text)
                            binding.inText.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                            binding.inText.compoundDrawablesRelative.forEach { drawable ->
                                drawable?.setTint(ContextCompat.getColor(requireContext(), R.color.green))
                            }
                        }
                        cancel()
                    }
                    is CommonState.Error -> {
                        baseActivity.loading.isGone()
                        parentFragmentManager.apiError(
                            message = res.message
                        )
                        cancel()
                    }
                }
            }
        }
    }
}