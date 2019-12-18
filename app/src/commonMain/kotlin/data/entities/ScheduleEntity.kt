package data.entities

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleEntity(
    val date: String,
    val rooms: List<RoomsEntity>,
    val timeSlots: List<TimeSlotEntity>)