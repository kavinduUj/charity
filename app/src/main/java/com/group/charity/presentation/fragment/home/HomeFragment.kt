package com.group.charity.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.group.charity.data.dto.allEvent.EventListResponse
import com.group.charity.databinding.HomeFragmentBinding
import com.group.charity.presentation.activity.BaseActivity
import com.group.util.CommonState
import com.group.util.PrefData
import com.group.util.apiError
import com.group.util.logOther
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment:Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val eventViewModel by viewModels<EventViewModel>()
    private lateinit var baseActivity: BaseActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(layoutInflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        baseActivity = requireActivity() as BaseActivity
        getEvent()
        logOther("userToken: ${Prefs.getString(PrefData.USER_TOKEN)}")
    }

    private fun getEvent() {
        eventViewModel.allEvents(
            "Bearer ${Prefs.getString(PrefData.USER_TOKEN)}"
        )
        lifecycleScope.launch {
            eventViewModel.eventStateFLow.collect { res->
                when(res) {
                    is CommonState.Loading -> {
                        baseActivity.loading.isVisible()
                    }

                    is CommonState.Success-> {
                        logOther("getAllEvent res: ${Gson().toJson(res.data)}")
                        setDataToAdapter(res.data)
                        cancel()
                        baseActivity.loading.isGone()
                    }

                    is CommonState.Error -> {
                        parentFragmentManager.apiError(
                            message = res.message
                        )
                        cancel()
                        baseActivity.loading.isGone()
                    }
                }
            }
        }
    }

    private fun setDataToAdapter(data: EventListResponse) {
        val gridLayoutManager =GridLayoutManager(requireContext(),1)
        val adapter = EventListAdapter(data)
        binding.eventRec.layoutManager = gridLayoutManager
        binding.eventRec.adapter = adapter
    }
}