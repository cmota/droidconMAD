package presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_speaker.view.*
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.droidcon.madrid.R
import domain.model.Info
import domain.model.Speaker
import presentation.cb.IOnUserSpeakerAction

class SpeakerListAdapter(private val speakers: List<Speaker>, private val action: IOnUserSpeakerAction): RecyclerView.Adapter<SpeakerListAdapter.SpeakerViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SpeakerViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return SpeakerViewHolder(
            inflater.inflate(
                R.layout.item_speaker,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return speakers.size
    }

    override fun onBindViewHolder(viewHolder: SpeakerViewHolder, position: Int) {
        val speaker = speakers[position]

        Glide.with(viewHolder.speakerPhoto)
             .load(speaker.profilePicture)
             .apply(RequestOptions.circleCropTransform())
             .placeholder(R.drawable.ic_filled_person)
             .into(viewHolder.speakerPhoto)

        viewHolder.speakerName.text = speaker.fullName
        viewHolder.speakerTalk.text = getSessions(speaker.sessions)
        viewHolder.container.setOnClickListener {
            action.onUserClickAction(speaker, it)
        }
    }

    private fun getSessions(sessions: List<Info>): String {
        var sessionsList: String = ""
        for (session in sessions) {
            sessionsList += "${session.name}, "
        }

        return sessionsList.substringBeforeLast(", ")
    }

    class SpeakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val container: RelativeLayout = itemView.rl_container

        val speakerPhoto: ImageView = itemView.iv_speaker_photo
        val speakerName: TextView = itemView.tv_speaker_name
        val speakerTalk: TextView = itemView.tv_speaker_talk
    }
}