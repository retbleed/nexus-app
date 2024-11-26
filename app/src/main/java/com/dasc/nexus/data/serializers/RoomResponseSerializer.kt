package com.dasc.nexus.data.serializers

import kotlinx.serialization.KSerializer
import com.dasc.nexus.data.models.RoomEntity
import com.dasc.nexus.data.utils.ApiResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.intOrNull

object RoomResponseSerializer : KSerializer<ApiResponse<List<RoomEntity>>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("RoomResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<List<RoomEntity>> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = tree["data"]?.jsonArray?.map { jsonElement ->
            input.json.decodeFromJsonElement(RoomEntity.serializer(), jsonElement)
        } ?: emptyList()

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<List<RoomEntity>>) {
        throw SerializationException("Serialization is not supported")
    }
}

object SingleRoomResponseSerializer : KSerializer<ApiResponse<RoomEntity>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("SingleRoomResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<RoomEntity> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = input.json.decodeFromJsonElement(RoomEntity.serializer(), tree["data"]!!)

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<RoomEntity>) {
        throw SerializationException("Serialization is not supported")
    }
}