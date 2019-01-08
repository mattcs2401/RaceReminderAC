package mcssoft.com.racereminderac.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class RaceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Don't panic, but your time is up!",
                Toast.LENGTH_LONG).show()
    }
}