package com.group.charity.data.dto

data class Event(
    val __v: Int,
    val _id: String,
    val aboutEvent: String,
    val attendUsers: List<String>,
    val comments: List<Comment>,
    val endDate: String,
    val eventName: String,
    val images: List<String>,
    val location: String,
    val startDate: String,
    val status: Int,
    val userId: String
)