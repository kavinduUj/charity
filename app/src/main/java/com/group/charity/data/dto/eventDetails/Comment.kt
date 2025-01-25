package com.group.charity.data.dto.eventDetails

data class Comment(
    val _id: String,
    val comment: String,
    val createdAt: String,
    val userId: UserId
)