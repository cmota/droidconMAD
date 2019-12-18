package presentation.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.droidcon.madrid.R
import domain.AppData
import domain.model.Session
import domain.model.Speaker
import kotlinx.android.synthetic.main.activity_session.*
import kotlinx.android.synthetic.main.activity_session.appbar
import kotlinx.android.synthetic.main.activity_session.collapsing_toolbar
import kotlinx.android.synthetic.main.activity_session.rv_content
import kotlinx.android.synthetic.main.activity_session.toolbar
import presentation.adapters.SpeakerListAdapter
import presentation.cb.IOnUserSpeakerAction
import utils.*
import kotlin.math.abs

class SessionActivity : AppCompatActivity(), IOnUserSpeakerAction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        val sessionId = intent.extras.getString(EXTRA_SESSION_ID)
        val session = AppData.getSessionById(sessionId)

        setup(session)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setup(session: Session) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                collapsing_toolbar.title = getString(R.string.toolbar_venue)
            } else {
                collapsing_toolbar.title = ""
            }
        });

        tv_talk_title.text = session.title
        tv_talk_schedule.text = getString(R.string.talk_duration,
            Utils.getFormattedDate(session.startsAt),
            Utils.getFormattedDate(session.endsAt))
        tv_talk_room.text = session.room
        tv_talk_description.text = session.summary

        val speakers = AppData.getSpeaker(session)
        if (speakers.isNullOrEmpty()) {
            tv_section_speakers.visibility = View.GONE
            return
        }

        val linearLayoutManager = LinearLayoutManager(applicationContext)

        rv_content.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = SpeakerListAdapter(AppData.getSpeaker(session), this@SessionActivity)
        }
    }

    //region IOnUserSpeakerAction
    override fun onUserClickAction(speaker: Speaker, view: View) {
        val intent = Intent(applicationContext, SpeakerActivity::class.java)
        intent.putExtra(EXTRA_SPEAKER_ID, speaker.id)
        startActivity(intent)
    }

    //endregion
}