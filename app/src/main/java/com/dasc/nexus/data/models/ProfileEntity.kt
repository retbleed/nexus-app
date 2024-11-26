package com.dasc.nexus.data.models

data class ProfileEntity(
    val id: Long,
    val userId: Int,
    val teacherId: Int,
    val name: String,
    val profilePicture: String?,
)