package presentation.fragments

import ServiceLocator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.droidcon.madrid.R
import domain.model.Schedule
import domain.model.Speaker
import kotlinx.android.synthetic.main.fragment_tab.*
import presentation.adapters.TabAdapter
import presentation.cb.IScheduleData
import presentation.cb.ISpeakersData
import utils.EXTRA_SCHEDULE_DAY
import utils.EXTRA_TAB_TITLE
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TabFragment"

class TabFragment : Fragment(), ISpeakersData, IScheduleData {

    private val presenterSpeakers by lazy { ServiceLocator.getSpeakersListPresenter }
    private val presenterSchedule by lazy { ServiceLocator.getScheduleListPresenter }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setup()

        presenterSpeakers.attachView(this)
        presenterSchedule.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onResume() {
        super.onResume()

        for(tab in (vp_container.adapter as TabAdapter).getItems()) {
            tab.onResume()
        }
    }

    override fun onDetach() {
        super.onDetach()

        presenterSpeakers.detachView()
        presenterSchedule.detachView()
    }

    private fun setup() {
        toolbar.title = getString(R.string.toolbar_schedule)

        val arg1 = Bundle()
        arg1.putString(EXTRA_TAB_TITLE, "20 ${getMonthNameShort(20, Calendar.DECEMBER)}")
        arg1.putString(EXTRA_SCHEDULE_DAY, "2019-12-20T00:00:00")

        val day1 = ScheduleFragment()
        day1.arguments = arg1


        val arg2 = Bundle()
        arg2.putString(EXTRA_TAB_TITLE, "21 ${getMonthNameShort(21, Calendar.DECEMBER)}")
        arg2.putString(EXTRA_SCHEDULE_DAY, "2019-12-21T00:00:00")

        val day2 = ScheduleFragment()
        day2.arguments = arg2


        val tabs = arrayListOf<Fragment>(day1, day2)
        val adapter = TabAdapter(fragmentManager!!, tabs)

        vp_container.adapter = adapter
        tl_container.setupWithViewPager(vp_container)
    }

    private fun getMonthNameShort(day: Int, month: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.set(Calendar.MONTH, month)
        return SimpleDateFormat("E", Locale.getDefault()).format(cal.time)
    }

    //region ISpeakersData
    override fun onSpeakersDataFetched(speakers: List<Speaker>) {
        //Do nothing
    }

    override fun onSpeakersDataFailed(e: Exception) {
        //Do nothing
    }

    //endregion

    //region IScheduleData
    override fun onScheduleDataFetched(schedule: List<Schedule>) {
        Log.d(TAG, "onScheduleDataFetched")
        for(tab in (vp_container.adapter as TabAdapter).getItems()) {
            (tab as ScheduleFragment).onScheduleDataFetched(schedule)
        }
    }

    override fun onScheduleDataFailed(e: Exception) {
        Log.d(TAG, "onScheduleDataFailed | exception=$e")
        for(tab in (vp_container.adapter as TabAdapter).getItems()) {
            (tab as ScheduleFragment).onScheduleDataFailed(e)
        }
    }
    //endregion
}