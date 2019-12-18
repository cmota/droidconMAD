package data.entities

import kotlinx.serialization.Serializable

@Serializable
data class TimeSlotEntity(
    val slotStart: String,
    val rooms: List<RoomEntity>)