package mcssoft.com.racereminderac.background.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Operation
import androidx.work.Result
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.R

class NotifyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val context: Context
    private val workerParams: WorkerParameters

    init {
        this.context = context
        this.workerParams = workerParams
    }

    override fun doWork(): Result {
        try {
            sendNotification("Testing", "A testing message.")
            return Result.success()
        } catch (ex: Exception) {
            return Result.failure()
        } finally {
            // TBA
        }
    }

    private fun sendNotification(title: String, message: String) {
//        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //If on Oreo then notification requires a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context /*applicationContext*/, "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notification.build())
    }
}