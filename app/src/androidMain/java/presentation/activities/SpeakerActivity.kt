package presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.droidcon.madrid.R
import domain.AppData
import domain.model.Session
import domain.model.Speaker
import kotlinx.android.synthetic.main.activity_speaker.*
import presentation.adapters.SessionListAdapter
import presentation.cb.IOnUserSessionAction
import utils.EXTRA_SESSION_ID
import utils.EXTRA_SPEAKER_ID

class SpeakerActivity : AppCompatActivity(), IOnUserSessionAction {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker)

        val speakerId = intent.extras!!.getString(EXTRA_SPEAKER_ID)
        setup(AppData.getSpeakerById(speakerId!!))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setup(speaker: Speaker) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        Glide.with(this)
            .load(speaker.profilePicture)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_filled_person)
            .into(iv_speaker_photo)

        tv_speaker_name.text = speaker.fullName
        tv_speaker_bio.text = speaker.bio

        val linearLayoutManager = LinearLayoutManager(applicationContext)

        rv_content.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = SessionListAdapter(AppData.getSession(speaker), this@SpeakerActivity)
        }
    }

    //region IOnUserSessionAction
    override fun onUserClickAction(session: Session, view: View) {
        val intent = Intent(applicationContext, SessionActivity::class.java)
        intent.putExtra(EXTRA_SESSION_ID, session.id)
        startActivity(intent)
    }

    override fun onUserUpdatedFavouriteState(session: Session) {
        // Do nothing
    }

    //endregion
}