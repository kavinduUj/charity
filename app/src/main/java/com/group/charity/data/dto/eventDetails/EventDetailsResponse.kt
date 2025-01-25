package com.group.charity.data.dto.eventDetails

data class EventDetailsResponse(
    val __v: Int,
    val _id: String,
    val aboutEvent: String,
    val attendUsers: List<Any>,
    val comments: List<Comment>,
    val endDate: String,
    val eventName: String,
    val images: List<String>,
    val location: String,
    val startDate: String,
    val status: Int,
    val userId: String
)