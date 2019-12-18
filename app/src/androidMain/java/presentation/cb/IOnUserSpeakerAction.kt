package presentation.cb

import android.view.View
import domain.model.Speaker

interface IOnUserSpeakerAction {

    fun onUserClickAction(speaker: Speaker, view: View)
}
