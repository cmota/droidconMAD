package domain.model

import data.entities.RoomEntity
import data.entities.TimeSlotEntity
import kotlinx.serialization.Serializable

@Serializable
data class TimeSlot (val slotStart: String,
                     val sessions: List<Session>)

fun TimeSlotEntity.toTimeSlot() = TimeSlot(
    slotStart = slotStart,
    sessions = getSessions(rooms))

private fun getSessions(rooms: List<RoomEntity>): List<Session> {
    val sessions = mutableListOf<Session>()
    for (room in rooms) {
        sessions += room.session.toSession()
    }
    return sessions
}