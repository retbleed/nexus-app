package com.dasc.nexus.data.repositories

import android.util.Log
import com.dasc.nexus.data.interfaces.IStudent
import com.dasc.nexus.data.models.StudentEntity
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.data.utils.TokenDataStore
import com.dasc.nexus.data.serializers.*
import com.dasc.nexus.data.utils.API_CONST
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

class StudentRepository(
    private val client: HttpClient,
    private val tokenDataStore: TokenDataStore
) : IStudent {
    override suspend fun getStudents(): Flow<Resource<List<StudentEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/students") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(StudentResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Students request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering students: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getStudentById(id: Long): Flow<Resource<StudentEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/students/$id") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(SingleStudentResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Student request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering student: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}