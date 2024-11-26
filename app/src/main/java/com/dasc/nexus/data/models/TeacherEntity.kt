package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TeacherEntity(
    val id: Long,
    val name: String,
    val lastname: String,
    val birthday: String,
    val email: String,
    val phone: String,
    val address: String
)