package com.dakshsemwal.nycschool.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * When there is a successful network call
     */
    class Success<T>(data: T?) : Resource<T>(data)
    /**
     * When network call fails and there is some error to be shown
     */
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}