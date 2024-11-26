package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RoomEntity(
    val id: Long,
    val name: String,
    val long_desc: String,
    val capacity: Int,
    val floor: Int,
    val building: Int,
    val type: RoomType = RoomType.SAL,
)

