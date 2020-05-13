package presentation.fragments

import ServiceLocator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.droidcon.madrid.R
import domain.AppData
import domain.model.Schedule
import domain.model.Session
import kotlinx.android.synthetic.main.fragment_schedule.*
import presentation.activities.SessionActivity
import presentation.adapters.ScheduleListAdapter
import presentation.cb.IOnUserSessionAction
import presentation.cb.IScheduleData
import utils.EXTRA_SCHEDULE_DAY
import utils.EXTRA_SESSION_ID

private const val TAG = "ScheduleFragment"

class ScheduleFragment : Fragment(), IScheduleData, IOnUserSessionAction {

    private val presenterSchedule by lazy { ServiceLocator.getScheduleListPresenter }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (isVisible) {
            val sessions = getSessionsFromSchedule(AppData.schedule)
            if (sessions.isNotEmpty()) {
                onDataSuccess()
                update(sessions)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenterSchedule.detachView()
    }

    private fun setup() {
        srl_reload.setOnRefreshListener {
            presenterSchedule.attachView(this)
        }

        val linearLayoutManager = LinearLayoutManager(context)

        rv_content.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = ScheduleListAdapter(mutableListOf(), this@ScheduleFragment)
        }
    }

    private fun update(sessions: List<Session>) {
        (rv_content.adapter as ScheduleListAdapter).update(sessions)
    }

    private fun getSessionsFromSchedule(schedule: List<Schedule>): List<Session> {
        val sessions = mutableListOf<Session>()

        for (day in schedule) {
            if (day.date == arguments?.get(EXTRA_SCHEDULE_DAY)) {
                for (slot in day.timeSlots) {
                    for (session in slot.sessions) {
                        sessions += session
                    }
                }
            }
        }

        return sessions
    }

    private fun onDataFailed() {
        activity!!.runOnUiThread {
            tv_no_data.visibility = View.VISIBLE
            tv_no_data.text = getString(R.string.schedule_empty)

            rv_content.visibility = View.GONE
        }
    }

    private fun onDataSuccess() {
        activity!!.runOnUiThread {
            tv_no_data.visibility = View.GONE
            rv_content.visibility = View.VISIBLE
        }
    }

    //region IScheduleData
    override fun onScheduleDataFetched(schedule: List<Schedule>) {
        Log.d(TAG, "schedule | onScheduleDataFetched")
        srl_reload.isRefreshing = false

        onDataSuccess()
        update(getSessionsFromSchedule(schedule))
    }

    override fun onScheduleDataFailed(e: Exception) {
        Log.d(TAG, "schedule | onScheduleDataFailed | exception=$e")
        srl_reload.isRefreshing = false

        if (rv_content.adapter == null || rv_content.adapter!!.itemCount == 0) {
            onDataFailed()
        }
    }
    //endregion

    //region IOnUserAction
    override fun onUserClickAction(session: Session, view: View) {
        val intent = Intent(context, SessionActivity::class.java)
        intent.putExtra(EXTRA_SESSION_ID, session.id)
        startActivity(intent)
    }

    override fun onUserUpdatedFavouriteState(session: Session) {
        session.favourite = !session.favourite
        AppData.updateSessionFavouriteState(session)
    }

    //endregion
}