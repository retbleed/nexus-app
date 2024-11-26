package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.models.RoomEntity
import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRoom {
    suspend fun getRooms(): Flow<Resource<List<RoomEntity>>>
    suspend fun getRoomById(id: Long): Flow<Resource<RoomEntity>>
}