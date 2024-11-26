package com.dasc.nexus.data.serializers

import com.dasc.nexus.data.models.StudentEntity
import com.dasc.nexus.data.utils.ApiResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

object StudentResponseSerializer : KSerializer<ApiResponse<List<StudentEntity>>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StudentResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<List<StudentEntity>> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = tree["data"]?.jsonArray?.map { jsonElement ->
            input.json.decodeFromJsonElement(StudentEntity.serializer(), jsonElement)
        } ?: emptyList()

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<List<StudentEntity>>) {
        throw SerializationException("Serialization is not supported")
    }
}

object SingleStudentResponseSerializer : KSerializer<ApiResponse<StudentEntity>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SingleStudentResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<StudentEntity> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = input.json.decodeFromJsonElement(StudentEntity.serializer(), tree["data"]!!)

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<StudentEntity>) {
        throw SerializationException("Serialization is not supported")
    }
}