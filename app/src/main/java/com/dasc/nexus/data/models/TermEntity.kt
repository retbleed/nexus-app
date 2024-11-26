package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TermEntity(
    val id: Long,
    val init: String?,
    val end: String?,
    val active: Int,
)