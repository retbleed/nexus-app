package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class StudentEntity(
    val id: Long,
    val name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val groups: List<GroupEntity> = emptyList(),
)