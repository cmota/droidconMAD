package domain.dao

import data.ScheduleDb
import domain.model.Schedule

class ScheduleDao(database: ScheduleDb) {

    private val db = database.scheduleModelQueries

    internal fun insertOrReplace(schedule: Schedule) {
        db.insertOrReplaceSchedule(
            id = schedule.date,
            schedule = schedule)
    }

    internal fun update(schedule: Schedule) {
        db.updateSchedule(
            schedule = schedule,
            id = schedule.date)
    }

    internal fun getAllSchedules(): List<Schedule> {
        val data = db.selectAllSchedules().executeAsList()

        val schedules = mutableListOf<Schedule>()
        for (item in data) {
            schedules += item.schedule
        }

        return schedules
    }
}