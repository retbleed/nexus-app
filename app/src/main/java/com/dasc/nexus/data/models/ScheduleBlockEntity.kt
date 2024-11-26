package com.dasc.nexus.data.models

import com.dasc.nexus.data.serializers.LocalTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class ScheduleBlockEntity(
    val id: Long,
    val day: String,
    @Serializable(with = LocalTimeSerializer::class) val start_time: LocalTime,
    @Serializable(with = LocalTimeSerializer::class) val end_time: LocalTime
)
