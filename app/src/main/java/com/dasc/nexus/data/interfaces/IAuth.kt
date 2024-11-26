package com.dasc.nexus.data.interfaces

import com.dasc.nexus.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAuth {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun logout(): Flow<Resource<Boolean>>
    suspend fun verifyToken(): Flow<Resource<Boolean>>
}