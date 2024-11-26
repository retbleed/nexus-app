package com.dasc.nexus.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleEntity(
    val id: Long,
    val teacher: TeacherEntity,
    val subject: SubjectEntity,
    val room: RoomEntity,
    val schedule_block: ScheduleBlockEntity,
    val term: TermEntity,
)
