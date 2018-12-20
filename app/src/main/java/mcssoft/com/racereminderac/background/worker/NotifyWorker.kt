package mcssoft.com.racereminderac.background.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants

class NotifyWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
// TODO - "fancy" up the notification ?

    override fun doWork(): Result {
        val message = createMessage()
        val id = inputData.keyValueMap["key_id"] as Long
        val title = context.resources.getString(R.string.nearing_race_time)

        try {
            displayNotification(title, message, id)
            return Result.success()
        } catch (ex: Exception) {
            return Result.failure()
        } finally {
            // TBA
        }
    }

    /**
     * Simple utility function to create the notification message.
     * @return A string from the input data values.
     */
    private fun createMessage() : String {
        val map = inputData.keyValueMap
        val cc = map["key_cc"] as String    // city code, notification display purposes.
        val rc  = map["key_rc"] as String   // race code, notification display purposes.
        val rn  = map["key_rn"] as String   // race num, notification display purposes.
        val rs  = map["key_rs"] as String   // race set, notification display purposes.
        val rt  = map["key_rt"] as String   // race time, notification display purposes.
        return "$cc$rc $rn $rs $rt"
    }

    /**
     * Simple utility function to display the Notification.
     * @param title: The notification's title.
     * @param message: The notification's message.
     * @param id: Object's id passed in the arguments to the notification's PendingIntent.
     */
    private fun displayNotification(title: String, message: String, id: Long) {
        /**
         * "Compat" methods used primarily as this project has min SDK version of 22.
         * The key is the NavDeepLinkBuilder method to create the PendingIntent.
         * See: https://proandroiddev.com/android-jetpack-navigation-to-the-rescue-fe588271d36
         *      https://developer.android.com/training/notify-user/build-notification
         */
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //If on Oreo then Notification requires a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Create the Notification.
        val notification = createNotification(title, message, id)

        // Display the notification.
        NotificationManagerCompat.from(context).notify(10, notification)
    }

    /**
     * Simple utility function to create the Notification.
     * @param title: The notification's title.
     * @param message: The notification's message.
     * @param id: Object's id passed in the arguments to the PendingIntent.
     * @return A Notification.
     */
    private fun createNotification(title: String, message: String, id: Long) : Notification {
        return NotificationCompat.Builder(context, "default")
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.DEFAULT_VIBRATE.toString())
                .setContentIntent(createIntent(id))
                .setAutoCancel(true)
                .build()
    }

    /**
     * Simple utility function to create a PendingIntent for the notification.
     * @param id: The object's id passed in the arguments.
     * @return A PendingIntent.
     */
    private fun createIntent(id: Long) : PendingIntent {
        return NavDeepLinkBuilder(context)
                .setGraph(R.navigation.navigation)
                .setDestination(R.id.id_edit_fragment)
                .setArguments(createArguments(id))
                .createPendingIntent()
    }

    /**
     * Simple utility function to create the arguments to be passed to the PendingIntent..
     * @param id: The object's id passed in the bundle.
     * @return A Bundle.
     */
    private fun createArguments(id: Long) : Bundle {
        return Bundle().apply {
            putLong(context.getString(R.string.key_edit_existing), id)
            putInt(context.getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        }
    }
}