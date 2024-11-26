package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CareerEntity(
    val id: Long,
    val name: String,
)