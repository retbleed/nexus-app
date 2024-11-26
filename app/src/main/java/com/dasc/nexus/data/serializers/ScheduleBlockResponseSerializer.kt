package com.dasc.nexus.data.serializers

import kotlinx.serialization.KSerializer
import com.dasc.nexus.data.models.ScheduleBlockEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.longOrNull
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object ScheduleBlockResponseSerializer : KSerializer<ScheduleBlockEntity> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ScheduleBlockEntity", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ScheduleBlockEntity {
        val input = decoder as? JsonDecoder ?: throw SerializationException("This class can be loaded only by Json")
        val tree = input.decodeJsonElement() as? JsonObject ?: throw SerializationException("Expected JsonObject")

        // Accessing the "schedule_block" object directly
        val dataObject = tree["schedule_block"]?.jsonObject
            ?: throw SerializationException("Missing 'schedule_block' object")

        val id = dataObject["id"]?.jsonPrimitive?.longOrNull ?: 0L
        val day = dataObject["day"]?.jsonPrimitive?.contentOrNull ?: ""
        val startTime = dataObject["start_time"]?.jsonPrimitive?.contentOrNull?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        } ?: LocalTime.of(0, 0)
        val endTime = dataObject["end_time"]?.jsonPrimitive?.contentOrNull?.let {
            LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME)
        } ?: LocalTime.of(0, 0)

        return ScheduleBlockEntity(id, day, startTime, endTime) // Returning ScheduleBlockEntity directly
    }

    override fun serialize(encoder: Encoder, value: ScheduleBlockEntity) {
        throw SerializationException("Serialization is not supported")
    }
}