package com.dasc.nexus.data.repositories


import android.util.Log
import com.dasc.nexus.data.interfaces.ISchedule
import com.dasc.nexus.data.models.ScheduleEntity
import com.dasc.nexus.data.serializers.ScheduleListResponseSerializer
import com.dasc.nexus.data.serializers.ScheduleResponseSerializer
import com.dasc.nexus.data.utils.API_CONST
import com.dasc.nexus.data.utils.ApiResponse
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

class ScheduleRepository(
    private val client: HttpClient,
    private val tokenDataStore: TokenDataStore
) : ISchedule {
    override suspend fun getScheduleById(id: Long): Flow<Resource<List<ScheduleEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = tokenDataStore.getToken().firstOrNull()
            val response = client.get("${API_CONST}api/teacher-schedules") { //POR AHORA SE VA QUEDAR ASI PERO SE TIENE QUE FIXEAR ASAP
                contentType(ContentType.Application.Json)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }

            if (response.status.isSuccess()) {
                val responseString = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val apiResponse = json.decodeFromString(ScheduleListResponseSerializer, responseString)
                emit(Resource.Success(apiResponse.data))
            } else {
                emit(Resource.Error("Schedule request failed: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("KtorRepository", "Error during recovering schedule: ${e.message}", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}