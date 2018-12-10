package mcssoft.com.racereminderac.background.worker

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.Result
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants

class NotifyWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
// TODO - "fancy" up the notification.

    override fun doWork(): Result {
        val map = inputData.keyValueMap
        val id = map.get("key_id") as Long       // object id passed in the arguments to the fragment.
        val cc = map.get("key_cc") as String    // city code, notification display purposes.
        val rc  = map.get("key_rc") as String   // race code, notification display purposes.
        val rn  = map.get("key_rn") as String   // race num, notification display purposes.
        val rs  = map.get("key_rs") as String   // race set, notification display purposes.
        val rt  = map.get("key_rt") as String   // race time, notification display purposes.

        try {
            sendNotification("Nearing Race time.", "$cc$rc $rn $rs $rt", id)
            return Result.success()
        } catch (ex: Exception) {
            return Result.failure()
        } finally {
            // TBA
        }
    }

    private fun sendNotification(title: String, message: String, id: Long) {
        /**
         * Notes (11/12/2018):
         * "Compat" methods used primarily as this project has min SDK version of 22.
         * The key is the NavDeepLinkBuilder method to create the pending intent.
         * See: https://proandroiddev.com/android-jetpack-navigation-to-the-rescue-fe588271d36
         */
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//        //If on Oreo then notification requires a notification channel.
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }

        // Prepare the arguments to pass in the notification.
        val arguments = Bundle().apply {
            putLong(context.getString(R.string.key_edit_existing), id)
            putInt(context.getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        }

        // Prepare the pending intent, while specifying the graph and destination.
        val pendingIndent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.navigation)
                .setDestination(R.id.id_edit_fragment)
                .setArguments(arguments)
                .createPendingIntent()

        // Create the notification instance.
        val notification = NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIndent)
                .setAutoCancel(true)
                .build()

        // Display the notification.
        NotificationManagerCompat.from(context).notify(10, notification)
//        notificationManager.notify(1, notification.build())
    }
}