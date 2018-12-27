package mcssoft.com.racereminderac.utility

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.eventbus.DeleteMessage
import org.greenrobot.eventbus.EventBus

class SnackBarCB(private val view: View, private val race: Race) : Snackbar.Callback() {

    @SuppressLint("SwitchIntDef") // <<-- this because not all events considered.
    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        // The UNDO timeout has expired or the Snackbar was swipe dismissed, so also remove from
        // database.
        when(event) {
            BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT -> {
                deleteRace()
            }
            BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_SWIPE -> {
                deleteRace()
            }
            BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION -> {
                // TBA
            }
        }
        // Re-instate bottom nav view.
        (view.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.VISIBLE
    }

    /**
     * Post the delete message on EventBus.
     */
    private fun deleteRace() {
        EventBus.getDefault().post(DeleteMessage(race))
    }

}