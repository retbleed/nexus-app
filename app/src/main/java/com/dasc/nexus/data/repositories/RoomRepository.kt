package com.dasc.nexus.data.repositories

import android.util.Log
import com.dasc.nexus.data.interfaces.IRoom
import com.dasc.nexus.data.models.RoomEntity
import com.dasc.nexus.data.serializers.RoomResponseSerializer
import com.dasc.nexus.data.serializers.SingleRoomResponseSerializer
import com.dasc.nexus.data.utils.API_CONST
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.data.utils.TokenDataStore
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class RoomRepository(
    private val client: HttpClient,
    private val tokenDataStore: TokenDataStore
) : IRoom {
    override suspend fun getRooms(): Flow<Resource<List<RoomEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/rooms") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(RoomResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Rooms request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering rooms: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getRoomById(id: Long): Flow<Resource<RoomEntity>> =
        flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/rooms/$id") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(SingleRoomResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Room request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering room: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}