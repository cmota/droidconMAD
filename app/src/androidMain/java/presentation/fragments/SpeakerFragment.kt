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
import domain.model.Speaker
import kotlinx.android.synthetic.main.fragment_speaker.*
import presentation.activities.SpeakerActivity
import presentation.adapters.SpeakerListAdapter
import presentation.cb.IOnUserSpeakerAction
import presentation.cb.ISpeakersData
import utils.EXTRA_SPEAKER_ID

private const val TAG = "SpeakerFragment"

class SpeakerFragment : Fragment(), ISpeakersData, IOnUserSpeakerAction {

    private val presenter by lazy { ServiceLocator.getSpeakersListPresenter }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setup()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speaker, container, false)
    }

    override fun onDetach() {
        super.onDetach()

        presenter.detachView()
    }

    private fun setup() {
        toolbar.title = getString(R.string.toolbar_speakers)

        srl_reload.setOnRefreshListener {
            presenter.attachView(this)
        }

        val speakers = AppData.speakers
        if (speakers.isNotEmpty()) {
            onDataSuccess()
            update(speakers)
        } else {
            presenter.attachView(this)
        }
    }

    private fun update(speakers: List<Speaker>) {
        val linearLayoutManager = LinearLayoutManager(context)

        rv_content.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = SpeakerListAdapter(speakers, this@SpeakerFragment)
        }
    }

    private fun onDataFailed() {
        tv_no_data.visibility = View.VISIBLE
        tv_no_data.text = getString(R.string.speakers_empty)

        rv_content.visibility = View.GONE
    }

    private fun onDataSuccess() {
        tv_no_data.visibility = View.GONE
        rv_content.visibility = View.VISIBLE
    }

    //region ISpeakersListView
    override fun onSpeakersDataFetched(speakers: List<Speaker>) {
        Log.d(TAG, "onSpeakersDataFetched")
        srl_reload.isRefreshing = false

        onDataSuccess()
        update(speakers)
    }

    override fun onSpeakersDataFailed(e: Exception) {
        Log.d(TAG, "onSpeakersDataFailed | exception=$e")
        srl_reload.isRefreshing = false

        if (rv_content.adapter == null || rv_content.adapter!!.itemCount == 0) {
            onDataFailed()
        }
    }

    //endregion

    //region IOnUserAction
    override fun onUserClickAction(speaker: Speaker, view: View) {
        val intent = Intent(context, SpeakerActivity::class.java)
        intent.putExtra(EXTRA_SPEAKER_ID, speaker.id)

        startActivity(intent)
    }

    //endregion
}