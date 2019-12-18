package presentation

import domain.GetSchedule
import domain.defaultDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.cb.IScheduleData
import kotlin.coroutines.CoroutineContext

class ScheduleListPresenter(private val schedule: GetSchedule,
                            private val coroutineContext: CoroutineContext = defaultDispatcher) {

    private var view: IScheduleData? = null
    private lateinit var scope: PresenterCoroutineScope

    fun attachView(currView: IScheduleData) {
        view = currView
        scope = PresenterCoroutineScope(coroutineContext)
        fetchScheduleList()
    }

    fun detachView() {
        if (view == null) {
            return
        }

        view = null
        scope.viewDetached()
    }

    private fun fetchScheduleList() {
        scope.launch {
            schedule(
                onSuccess = { view?.onScheduleDataFetched(it) },
                onFailure = { view?.onScheduleDataFailed(it) }
            )
        }
    }
}