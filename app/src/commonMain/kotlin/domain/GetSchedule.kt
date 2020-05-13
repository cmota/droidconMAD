package domain

import data.SessionizeAPI
import domain.dao.ScheduleDao
import domain.dao.SessionDao
import domain.model.Schedule
import domain.model.Session
import domain.model.toSchedule
import kotlinx.coroutines.coroutineScope

class GetSchedule(private val sessionizeAPI: SessionizeAPI, private val scheduleDao: ScheduleDao, private val sessionDao: SessionDao) {

    suspend operator fun invoke(onSuccess: (List<Schedule>) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val result = sessionizeAPI.fetchSchedule()

            coroutineScope {
                val currSchedule = scheduleDao.getAllSchedules()

                val schedule = mutableListOf<Schedule>()
                for (date in result) {
                    val newSchedule = date.toSchedule()
                    for (timeSlot in newSchedule.timeSlots) {
                        for (session in timeSlot.sessions) {
                            session.favourite = getFavouriteStateFromDB(currSchedule, session)
                        }
                    }

                    schedule += newSchedule
                    scheduleDao.insertOrReplace(newSchedule)
                }

                val sessions = getAllSessions(schedule)

                AppData.sessions = sessions
                AppData.schedule = schedule

                for (session in sessions) {
                    sessionDao.insertOrReplace(session)
                }

                onSuccess(schedule)

            }
        } catch (e: Exception) {
            coroutineScope {
                val sessions = sessionDao.getAllSessions()
                if (sessions.isNullOrEmpty()) {
                    onFailure(e)
                } else {
                    val schedule = scheduleDao.getAllSchedules()

                    AppData.sessions = sessions
                    AppData.schedule = schedule

                    onSuccess(schedule)
                }
            }
        }
    }

    private fun getAllSessions(schedule: List<Schedule>): List<Session> {
        val sessions = mutableListOf<Session>()
        for (date in schedule) {
            for (slot in date.timeSlots) {
                sessions += slot.sessions
            }
        }

        return sessions
    }

    private fun getFavouriteStateFromDB(currSchedule: List<Schedule>, currSession: Session): Boolean {
        for (schedule in currSchedule) {
            for (timeSlot in schedule.timeSlots) {
                for (session in timeSlot.sessions) {
                    if (session.id == currSession.id) {
                        return session.favourite
                    }
                }
            }
        }

        return false
    }
}
