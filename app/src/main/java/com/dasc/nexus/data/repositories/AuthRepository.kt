package com.dasc.nexus.data.repositories

import android.util.Log
import com.dasc.nexus.data.interfaces.IAuth
import com.dasc.nexus.data.serializers.AuthResponseSerializer
import com.dasc.nexus.data.utils.API_CONST
import com.dasc.nexus.data.utils.ApiResponse
import com.dasc.nexus.data.utils.Resource
import com.dasc.nexus.data.utils.TokenDataStore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class AuthRepository(
    private val client: HttpClient,
    private val tokenDataStore: TokenDataStore
) : IAuth {

    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.post("${API_CONST}api/login") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email, "password" to password))
            }
            val responseString = response.bodyAsText()
            val json = Json { ignoreUnknownKeys = true }
            val apiResponse = json.decodeFromString(AuthResponseSerializer, responseString)

            if (apiResponse.status == 200) {
                val token = apiResponse.data as? String
                if (token != null) {
                    tokenDataStore.saveToken(token)
                } else {
                    emit(Resource.Error("Invalid token format"))
                    return@flow
                }
            }

            emit(Resource.Success(apiResponse.status == 200))
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during login: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun logout(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.post("${API_CONST}api/logout") {
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(AuthResponseSerializer, responseString)
                if (apiResponse.status == 200) { tokenDataStore.clearToken() }
                emit(Resource.Success(apiResponse.status == 200))
            } else {
                emit(Resource.Error("Logout failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during logout: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun verifyToken(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            emit(Resource.Success(token != null))
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during logout: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}