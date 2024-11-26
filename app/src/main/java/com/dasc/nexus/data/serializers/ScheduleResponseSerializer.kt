package com.dasc.nexus.data.serializers

import com.dasc.nexus.data.models.*
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
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.longOrNull
import java.time.LocalTime

object ScheduleResponseSerializer : KSerializer<ApiResponse<ScheduleEntity>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ScheduleResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<ScheduleEntity> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = tree["data"]?.jsonObject?.let { dataObject ->
            val id = dataObject["id"]?.jsonPrimitive?.longOrNull ?: 0L
            val teacher = input.json.decodeFromJsonElement(TeacherEntity.serializer(), dataObject["teacher"]!!)
            val subject = input.json.decodeFromJsonElement(SubjectEntity.serializer(), dataObject["subject"]!!)
            val room = input.json.decodeFromJsonElement(RoomEntity.serializer(), dataObject["room"]!!)
            val scheduleBlock = input.json.decodeFromJsonElement(ScheduleBlockEntity.serializer(), dataObject["schedule_block"]!!)
            val term = input.json.decodeFromJsonElement(TermEntity.serializer(), dataObject["term"]!!)

            ScheduleEntity(id, teacher, subject, room, scheduleBlock, term)
        } ?: ScheduleEntity(0L, TeacherEntity(0L, "", "", "", "", "", ""), SubjectEntity(0L, "", "", 0, 0, 0, 0, 0), RoomEntity(0L, "", "", 0, 0, 0), ScheduleBlockEntity(0L, "",
            LocalTime.of(0, 0),
            LocalTime.of(0, 0)), TermEntity(0L, "", "", 0))

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<ScheduleEntity>) {
        throw SerializationException("Serialization is not supported")
    }
}

object ScheduleListResponseSerializer : KSerializer<ApiResponse<List<ScheduleEntity>>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ScheduleListResponse", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ApiResponse<List<ScheduleEntity>> {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        val data = tree["data"]?.jsonArray?.map { jsonElement ->
            input.json.decodeFromJsonElement(ScheduleEntity.serializer(), jsonElement)
        } ?: emptyList()

        val message = tree["message"]?.jsonPrimitive?.content ?: ""
        val status = tree["status"]?.jsonPrimitive?.intOrNull ?: 0

        return ApiResponse(data = data, message = message, status = status)
    }

    override fun serialize(encoder: Encoder, value: ApiResponse<List<ScheduleEntity>>) {
        throw SerializationException("Serialization is not supported")
    }
}