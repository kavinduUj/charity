package com.group.util

object EndPoints {
    const val LOGIN = "auth/login"
    const val SIGN_UP = "auth/register"
    const val ALL_EVENT = "events/allEvents/{status}"
    const val ADD_EVENT = "events/add"
    const val EVENT_DETAILS = "events/{eventId}"
    const val ATTEND_EVENT = "events/{eventId}/attend"
}