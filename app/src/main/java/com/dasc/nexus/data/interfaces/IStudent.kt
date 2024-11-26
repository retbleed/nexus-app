package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.StudentEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IStudent {
    suspend fun getStudents(): Flow<Resource<List<StudentEntity>>>
    suspend fun getStudentById(id: Long): Flow<Resource<StudentEntity>>
}