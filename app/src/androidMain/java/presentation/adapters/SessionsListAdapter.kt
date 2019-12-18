package presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.droidcon.madrid.R
import domain.model.Session
import kotlinx.android.synthetic.main.item_session.view.*
import presentation.cb.IOnUserSessionAction
import utils.Utils

class SessionListAdapter(private val sessions: List<Session>, private val action: IOnUserSessionAction): RecyclerView.Adapter<SessionListAdapter.SessionViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): SessionViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return SessionViewHolder(
            inflater.inflate(
                R.layout.item_session,
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

        viewHolder.talkTitle.text = session.title

        val time = viewHolder.container.context.getString(R.string.talk_time_and_room,
            Utils.getFormattedDate(session.startsAt),
            session.room)
        viewHolder.talkTime.text = time

        viewHolder.container.setOnClickListener {
            action.onUserClickAction(session, it)
        }
    }

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: LinearLayout = itemView.ll_container

        val talkTitle: TextView = itemView.tv_talk_title
        val talkTime: TextView = itemView.tv_time
    }
}