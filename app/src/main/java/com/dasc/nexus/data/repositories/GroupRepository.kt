package com.dasc.nexus.data.repositories

import android.util.Log
import com.dasc.nexus.data.interfaces.IGroup
import com.dasc.nexus.data.models.GroupEntity
import com.dasc.nexus.data.serializers.GroupResponseSerializer
import com.dasc.nexus.data.serializers.SingleGroupResponseSerializer
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

class GroupRepository(
    private val client: HttpClient,
    private val tokenDataStore: TokenDataStore
) : IGroup {
    override suspend fun getGroups(): Flow<Resource<List<GroupEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/groups") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(GroupResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Groups request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering groups: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getGroupById(id: Long): Flow<Resource<GroupEntity>> =
        flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/groups/$id") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(SingleGroupResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Group request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering group: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}