package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.TermEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ITerm {
    suspend fun getTerms(): Flow<Resource<List<TermEntity>>>
    suspend fun getTermById(id: Long): Flow<Resource<TermEntity>>
}