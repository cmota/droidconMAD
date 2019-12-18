package presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidcon.madrid.R
import domain.AppData
import domain.model.Session
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_favourites.rv_content
import presentation.activities.SessionActivity
import presentation.adapters.ScheduleListAdapter
import presentation.cb.IOnUserSessionAction
import utils.EXTRA_SESSION_ID

class FavouritesFragment : Fragment(), IOnUserSessionAction {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.title = getString(R.string.toolbar_favourites)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onResume() {
        super.onResume()

        setup()
    }

    private fun setup() {
        val sessions = AppData.getAllFavouriteSessions()
        if (sessions.isNullOrEmpty()) {
            tv_no_data.visibility = View.VISIBLE
            rv_content.visibility = View.GONE
            return
        }

        tv_no_data.visibility = View.GONE
        rv_content.visibility = View.VISIBLE

        val linearLayoutManager = LinearLayoutManager(context)

        rv_content.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = ScheduleListAdapter(sessions, this@FavouritesFragment)
        }
    }

    //region IOnUserAction
    override fun onUserClickAction(session: Session, view: View) {
        val intent = Intent(context, SessionActivity::class.java)
        intent.putExtra(EXTRA_SESSION_ID, session.id)
        startActivity(intent)
    }

    override fun onUserUpdatedFavouriteState(session: Session) {
        session.favourite = !session.favourite
        AppData.updateSessionFavouriteState(session)

        setup()
    }

    //endregion
}