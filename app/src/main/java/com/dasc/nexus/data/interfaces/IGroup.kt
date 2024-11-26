package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.GroupEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IGroup {
    suspend fun getGroups(): Flow<Resource<List<GroupEntity>>>
    suspend fun getGroupById(id: Long): Flow<Resource<GroupEntity>>
}