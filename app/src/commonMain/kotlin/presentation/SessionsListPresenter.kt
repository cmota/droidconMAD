package presentation

import domain.GetSessions
import domain.defaultDispatcher
import kotlinx.coroutines.launch
import presentation.cb.ISessionsData
import kotlin.coroutines.CoroutineContext

class SessionsListPresenter(private val sessions: GetSessions,
                            private val coroutineContext: CoroutineContext = defaultDispatcher) {

    private var view: ISessionsData? = null
    private lateinit var scope: PresenterCoroutineScope

    fun attachView(currView: ISessionsData) {
        view = currView
        scope = PresenterCoroutineScope(coroutineContext)
        fetchSessionsList()
    }

    fun detachView() {
        if (view == null) {
            return
        }

        view = null
        scope.viewDetached()
    }

    private fun fetchSessionsList() {
        scope.launch {
            sessions(
                onSuccess = { view?.onSessionsDataFetched(it) },
                onFailure = { view?.onSessionsDataFailed(it) }
            )
        }
    }
}