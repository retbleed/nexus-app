package com.dasc.nexus.data.serializers

import com.dasc.nexus.data.models.TeacherEntity
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
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray

object TeacherResponseSerializer : KSerializer<ApiResponse<List<TeacherEntity>>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("TeacherResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<List<TeacherEntity>> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = tree["data"]?.jsonArray?.map { jsonElement ->
            input.json.decodeFromJsonElement(TeacherEntity.serializer(), jsonElement)
        } ?: emptyList()

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<List<TeacherEntity>>) {
        throw SerializationException("Serialization is not supported")
    }
}

object SingleTeacherResponseSerializer : KSerializer<ApiResponse<TeacherEntity>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SingleTeacherResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<TeacherEntity> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = input.json.decodeFromJsonElement(TeacherEntity.serializer(), tree["data"]!!)

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<TeacherEntity>) {
        throw SerializationException("Serialization is not supported")
    }
}