package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.ScheduleEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ISchedule {
    suspend fun getScheduleById(id: Long): Flow<Resource<List<ScheduleEntity>>> // Changed return type to List<ScheduleEntity>
}