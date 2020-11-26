package com.ic.flckr.common.domain

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Fail(val message: String, val error: Throwable? = null) : Result<Nothing>()
}