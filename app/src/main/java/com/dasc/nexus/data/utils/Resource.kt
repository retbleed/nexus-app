package com.dasc.nexus.data.utils

import android.util.Log

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Warning<T>(message: String?, data: T? = null): Resource<T>(data, message)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message){
        init {
            Log.d("ResourceError", message ?: "Unknown error")
        }
    }
    class Loading<T>(data: T? = null): Resource<T>(data)
}