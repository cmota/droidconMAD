package presentation.cb

import domain.model.Session

interface ISessionsData {

    fun onSessionsDataFetched(sessions: List<Session>)

    fun onSessionsDataFailed(e: Exception)
}