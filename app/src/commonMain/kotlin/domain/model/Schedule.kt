package domain.model

import data.entities.ScheduleEntity
import data.entities.TimeSlotEntity
import kotlinx.serialization.Serializable

@Serializable
data class Schedule (val date: String,
                     val timeSlots: List<TimeSlot>)

fun ScheduleEntity.toSchedule() = Schedule(
    date = date,
    timeSlots = getTimeSlots(timeSlots)
)

private fun getTimeSlots(timeSlots: List<TimeSlotEntity>): List<TimeSlot> {
    val timeSlotsList = mutableListOf<TimeSlot>()
    for (slot in timeSlots) {
        timeSlotsList += slot.toTimeSlot()
    }

    return timeSlotsList
}