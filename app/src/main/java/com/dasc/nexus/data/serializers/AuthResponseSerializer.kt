package com.dasc.nexus.data.serializers

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
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

object AuthResponseSerializer : KSerializer<ApiResponse<String?>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("AuthResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<String?> { // Changed return type
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val token = tree["data"]?.jsonPrimitive?.contentOrNull
        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = token, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<String?>) {
        throw SerializationException("Serialization is not supported")
    }
}