package com.dasc.nexus.data.utils

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @Contextual val data: T,
    val message: String,
    val status: Int
)
