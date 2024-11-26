package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.TeacherEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ITeacher {
    suspend fun getTeachers(): Flow<Resource<List<TeacherEntity>>>
    suspend fun getTeacherById(id: Long): Flow<Resource<TeacherEntity>>
}