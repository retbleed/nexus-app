package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.SubjectEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ISubject {
    suspend fun getSubjects(): Flow<Resource<List<SubjectEntity>>>
    suspend fun getSubjectById(id: Long): Flow<Resource<SubjectEntity>>
}