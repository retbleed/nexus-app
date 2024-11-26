package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GroupEntity(
    val id: Long,
    val semester: Int,
    val shift: ShiftType
)