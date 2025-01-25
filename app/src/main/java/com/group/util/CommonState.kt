package com.group.util

sealed class CommonState<out T> {
    object Loading : CommonState<Nothing>()
    data class Success<out T>(val data: T) : CommonState<T>()
    data class Error(val message: String) : CommonState<Nothing>()
}