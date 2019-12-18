package domain.dao

import data.ScheduleDb
import domain.model.Session

class SessionDao(database: ScheduleDb) {

    private val db = database.sessionModelQueries

    internal fun insertOrReplace(session: Session) {
        db.insertOrReplaceSession(
            id = session.id,
            session = session)
    }

    internal fun update(session: Session) {
        db.updateSession(
            session = session,
            id = session.id)
    }

    internal fun getAllSessions(): List<Session> {
        val data = db.selectAllSessions().executeAsList()

        val speakers = mutableListOf<Session>()
        for (item in data) {
            speakers += item.session
        }

        return speakers
    }
}