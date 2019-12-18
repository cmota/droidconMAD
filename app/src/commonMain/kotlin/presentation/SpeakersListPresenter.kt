package presentation

import domain.GetSpeakers
import domain.defaultDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.cb.ISpeakersData
import kotlin.coroutines.CoroutineContext

class SpeakersListPresenter(private val speakers: GetSpeakers,
                            private val coroutineContext: CoroutineContext = defaultDispatcher) {

    private var view: ISpeakersData? = null
    private lateinit var scope: PresenterCoroutineScope

    fun attachView(currView: ISpeakersData) {
        view = currView
        scope = PresenterCoroutineScope(coroutineContext)
        fetchSpeakersList()
    }

    fun detachView() {
        if (view == null) {
            return
        }

        view = null
        scope.viewDetached()
    }

    private fun fetchSpeakersList() {
        scope.launch {
            speakers(
                onSuccess = { view?.onSpeakersDataFetched(it) },
                onFailure = { view?.onSpeakersDataFailed(it) }
            )
        }
    }
}