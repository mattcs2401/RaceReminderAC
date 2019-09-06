package mcssoft.com.racereminderac.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import mcssoft.com.racereminderac.utility.singleton.RaceWorkManager

// https://codelabs.developers.google.com/codelabs/android-training-broadcast-receivers/index.html?index=..%2F..%2Fandroid-training#0

class RaceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                Toast.makeText(context,"Hello from RaceReceiver", Toast.LENGTH_SHORT).show()

                RaceWorkManager.getInstance(context).processRaceDetails(id)
            }
        }

    }
}