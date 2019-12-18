package domain

import data.SessionizeAPI
import domain.dao.SessionDao
import domain.model.Session
import domain.model.toSession
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class GetSessions(private val sessionizeAPI: SessionizeAPI, private val sessionDao: SessionDao) {

    suspend operator fun invoke(onSuccess: (List<Session>) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val result = sessionizeAPI.fetchSessions()

            coroutineScope {

                    val currSessions = sessionDao.getAllSessions()

                    val sessions = mutableListOf<Session>()
                    for (setOfSessions in result) {
                        for (session in setOfSessions.sessions) {
                            val newSession = session.toSession()
                            newSession.favourite = getFavouriteStateFromDb(currSessions, newSession)

                            sessions += newSession
                            sessionDao.insertOrReplace(newSession)
                        }
                    }

                    AppData.sessions = sessions

                    onSuccess(sessions)
            }
        } catch (e: Exception) {
            coroutineScope {
                launch(uiDispatcher) {
                    val sessions = sessionDao.getAllSessions()
                    if (sessions.isNullOrEmpty()) {
                        onFailure(e)
                    } else {
                        AppData.sessions = sessions
                        onSuccess(sessions)
                    }
                }
            }
        }
    }

    private fun getFavouriteStateFromDb(currSessions: List<Session>, session: Session): Boolean {
        val dbSession = currSessions.filter { it.id == session.id }
        return dbSession.first().favourite
    }
}
