package mcssoft.com.racereminderac.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import mcssoft.com.racereminderac.utility.eventbus.ManualRefreshMessage
import org.greenrobot.eventbus.EventBus

class RaceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // TBA this actually works.
        EventBus.getDefault().post(ManualRefreshMessage())
    }
}