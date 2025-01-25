package com.group.charity.presentation.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.group.charity.R
import com.group.charity.data.dto.allEvent.EventListResponse
import com.group.charity.data.dto.allEvent.EventListResponseItem
import com.group.charity.databinding.EventAdapterViewBinding
import com.group.util.base64ToBitmap
import com.group.util.toFormattedDate

class EventListAdapter(
    private val data: EventListResponse
) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    lateinit var binding: EventAdapterViewBinding
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListAdapter.ViewHolder {
        binding = EventAdapterViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventListAdapter.ViewHolder, position: Int) = holder.run {
        bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: EventAdapterViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventListResponseItem) {
            binding.apply {
                title.text = event.eventName
                des.text = event.aboutEvent
                location.text = event.location
                date.text = event.startDate.toFormattedDate()
                Glide.with(context)
                    .load(if (event.images[0].contains("http"))event.images[0] else event.images[0].base64ToBitmap())
                    .placeholder(R.drawable.event_cover)
                    .error(R.drawable.event_cover)
                    .centerCrop()
                    .into(binding.coverImg)
            }
        }
    }
}