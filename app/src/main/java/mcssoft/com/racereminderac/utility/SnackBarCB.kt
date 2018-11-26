package mcssoft.com.racereminderac.utility

import android.annotation.SuppressLint
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.utility.eventbus.RemoveMessage
import org.greenrobot.eventbus.EventBus

class SnackBarCB(race: Race) : Snackbar.Callback() {

    private var race: Race

    init {
        this.race = race
    }

    @SuppressLint("SwitchIntDef") // <<-- this because not all events considered.
    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        // The UNDO timeout has expired or the Snackbar was swipe dismissed, so also remove from
        // database.
        when(event) {
            BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT -> {
                removeRace()
            }
            BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_SWIPE -> {
                removeRace()
            }
        }
    }

    private fun removeRace() {
        EventBus.getDefault().post(RemoveMessage(race))
    }
}