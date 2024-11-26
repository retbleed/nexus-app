package com.dasc.nexus.data.repositories

import android.util.Log
import com.dasc.nexus.data.interfaces.ITeacher
import com.dasc.nexus.data.models.TeacherEntity
import com.dasc.nexus.data.serializers.SingleTeacherResponseSerializer
import com.dasc.nexus.data.serializers.TeacherResponseSerializer
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

class TeacherRepository(
    private val client: HttpClient,
    private val tokenDataStore: TokenDataStore
) : ITeacher {
    override suspend fun getTeachers(): Flow<Resource<List<TeacherEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("api/teachers") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(TeacherResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Teachers request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering teachers: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getTeacherById(id: Long): Flow<Resource<TeacherEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("api/teachers/$id") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(
                    SingleTeacherResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Teacher request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering teacher: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}