package mcssoft.com.racereminderac.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.dao.RaceDAO
import mcssoft.com.racereminderac.database.RaceDatabase
import mcssoft.com.racereminderac.utility.RaceTime

class NotifyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val context: Context
    private val workerParams: WorkerParameters
    private var raceDao: RaceDAO

    init {
        this.context = context
        this.workerParams = workerParams
        raceDao = RaceDatabase.getInstance(context)!!.raceDao()
    }

    override fun doWork(): Result {
        // Get the current time, and compare against the entries in the database.
        val time = RaceTime.getInstance(context).getFormattedTime()

        val races = raceDao.getAllRacesBasic()

        for(race in races) {

            val bp = ""
        }

        sendNotification("Testing","A testing message.")
        return Result.SUCCESS
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //If on Oreo then notification requires a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notification.build())
    }
}