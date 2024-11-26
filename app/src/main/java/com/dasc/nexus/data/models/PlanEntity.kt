package com.dasc.nexus.data.models

import java.time.LocalDateTime

data class PlanEntity(
    val id: Long,
    val careerId: Long,
    val name: String,
    val init: LocalDateTime?,
    val end: LocalDateTime?,
)