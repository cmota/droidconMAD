package presentation.cb

import android.view.View
import domain.model.Session

interface IOnUserSessionAction {

    fun onUserClickAction(session: Session, view: View)

    fun onUserUpdatedFavouriteState(session: Session)
}
