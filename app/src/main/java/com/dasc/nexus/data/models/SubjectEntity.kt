package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SubjectEntity (
    val id: Long,
    val name: String,
    val key: String,
    val online: Int,
    val credits: Int,
    val theory_hours: Int,
    val practice_hours: Int,
    val total_hours: Int,
)