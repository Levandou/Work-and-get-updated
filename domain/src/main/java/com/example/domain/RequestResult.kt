package com.example.domain

sealed class RequestResult<out T> {
    data class Success<T>(val data: T) : RequestResult<T>()
    data class Warning<out T>(val info: String, val response: T?) : RequestResult<T>()
    data class Error(val throwable: Throwable) : RequestResult<Nothing>()
}