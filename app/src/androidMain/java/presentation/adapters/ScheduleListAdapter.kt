package presentation.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_schedule.view.*
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.droidcon.madrid.R
import domain.model.Category
import domain.model.Info
import domain.model.Session
import presentation.cb.IOnUserSessionAction
import utils.Utils

class ScheduleListAdapter(private var sessions: List<Session>, private val action: IOnUserSessionAction): RecyclerView.Adapter<ScheduleListAdapter.SessionViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SessionViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return SessionViewHolder(
            inflater.inflate(
                R.layout.item_schedule,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    override fun onBindViewHolder(viewHolder: SessionViewHolder, position: Int) {
        val session = sessions[position]

        if (position > 0 && sessions[position-1].startsAt == session.startsAt) {
            viewHolder.talkTimeSlot.visibility = View.INVISIBLE
        } else {
            viewHolder.talkTimeSlot.visibility = View.VISIBLE
            viewHolder.talkTimeSlot.text = Utils.getFormattedDate(session.startsAt)
        }

        viewHolder.talkTitle.text = session.title

        if (session.speakers.isEmpty()) {
            viewHolder.talkSpeaker.visibility = View.GONE
        } else {
            viewHolder.talkSpeaker.visibility = View.VISIBLE
            viewHolder.talkSpeaker.text = getSpeakersName(session.speakers)
        }

        if (session.room.isNullOrEmpty()) {
            viewHolder.talkRoom.visibility = View.GONE
        } else {
            viewHolder.talkRoom.visibility = View.VISIBLE
            viewHolder.talkRoom.text = session.room
        }

        viewHolder.talkFavourite.isSelected = session.favourite

        viewHolder.container.setOnClickListener {
            action.onUserClickAction(session, it)
        }

        viewHolder.talkFavourite.setOnClickListener {
            action.onUserUpdatedFavouriteState(session)
            viewHolder.talkFavourite.isSelected = session.favourite
        }
    }

    fun update(newSessions: List<Session>) {
        val diffUtil = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(old: Int, new: Int): Boolean {
                return sessions[old].id == newSessions[new].id
            }

            override fun areContentsTheSame(old: Int, new: Int): Boolean {
                return sessions[old] == newSessions[new]
            }

            override fun getOldListSize() = sessions.size

            override fun getNewListSize() = newSessions.size
        })

        diffUtil.dispatchUpdatesTo(this)
        sessions = newSessions
    }

    private fun getSpeakersName(speakers: List<Info>): String {
        var speakersName: String = ""
        for (info in speakers) {
            speakersName += "${info.name}, "
        }

        return speakersName.substringBeforeLast(", ")
    }

    private fun getCategories(categories: List<Category>): String {
        var categoriesList: String = ""
        for (category in categories) {
            categoriesList += "${category.name}, "
        }

        return categoriesList.substringBeforeLast(", ")
    }

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: RelativeLayout = itemView.rl_container

        val talkTimeSlot: TextView = itemView.tv_timeslot

        val talkTitle: TextView = itemView.tv_talk_title
        val talkSpeaker: TextView = itemView.tv_talk_speaker
        val talkRoom: TextView = itemView.tv_room

        val talkFavourite: ImageView = itemView.iv_favourite
    }
}