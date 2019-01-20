package mcssoft.com.racereminderac.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class RaceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // TODO - action the alarm.
        Toast.makeText(context, "TODO - action the alarm.", Toast.LENGTH_SHORT).show()
    }
}