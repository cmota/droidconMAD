package domain

import domain.model.Schedule
import domain.model.Session
import domain.model.Speaker
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object AppData {

    var speakers: List<Speaker> = mutableListOf()

    var sessions: List<Session> = mutableListOf()

    var schedule: List<Schedule> = mutableListOf()

    fun getSession(speaker: Speaker): List<Session> {
        val sessionsList = mutableListOf<Session>()
        for (session in sessions) {
            for (sessionSpeaker in session.speakers) {
                if (sessionSpeaker.id == speaker.id) {
                    sessionsList += session
                }
            }
        }

        return sessionsList
    }

    fun getAllFavouriteSessions(): List<Session> {
        return sessions.filter { it.favourite }
    }

    fun updateSessionFavouriteState(updatedSession: Session) {
        for (session in sessions) {
            if (session.id == updatedSession.id) {
                session.favourite = updatedSession.favourite
                ServiceLocator.sessionDao.update(session)

                updateScheduleFavouriteState(session)
            }
        }
    }

    private fun updateScheduleFavouriteState(updatedSession: Session) {
        for (item in schedule) {
            for (timeSlot in item.timeSlots) {
                for (session in timeSlot.sessions) {
                    if (session.id == updatedSession.id) {
                        session.favourite = updatedSession.favourite

                        val schedule = getScheduleBySession(session)
                        if (schedule != null) {
                            ServiceLocator.scheduleDao.update(schedule)
                        }
                    }
                }
            }
        }
    }

    fun getSessionById(id: String): Session {
        return sessions.first { it.id == id }
    }

    fun getScheduleBySession(session: Session): Schedule? {
        for (item in schedule) {
            for (timeSlot in item.timeSlots) {
                for (curSession in timeSlot.sessions) {
                    if (curSession.id == session.id) {
                        return item
                    }
                }
            }
        }

        return null
    }

    fun getSpeaker(session: Session): List<Speaker> {
        val speakersList = mutableListOf<Speaker>()
        for (speaker in speakers) {
            for (speakerSession in speaker.sessions) {
                if (speakerSession.id == session.id) {
                    speakersList += speaker
                }
            }
        }

        return speakersList
    }

    fun getSpeakerById(id: String): Speaker {
        return speakers.first { it.id == id }
    }
}