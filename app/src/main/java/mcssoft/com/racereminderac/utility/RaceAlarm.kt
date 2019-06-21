package mcssoft.com.racereminderac.utility

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

/**
 * Utility class as wrapper for AlarmManager.
 */
class RaceAlarm {

    /**
     * For Singleton instance.
     * @Note: Can't use Context here, so context passed into the setAlarm() method.
     */
    companion object {
        @Volatile private var instance: RaceAlarm? = null

        fun getInstance(): RaceAlarm? {
            if (instance == null) {
                synchronized(RaceAlarm::class) {
                    instance = RaceAlarm()
                }
            }
            return instance
        }
    }

    /**
     * Set the UI refresh alarm.
     * @param context: The context being used.
     * @param interval: The alarm time in minutes.
     */
    internal fun setAlarm(context: Context, interval: Long) {
        // Cancel any previously set alarm.
        cancelAlarm()
        // Set the interval equivalent in mSec.
        val millis = interval * 60 * 1000
        // Set the alarm manager and intent.
        val intent = Intent(context, RaceReceiver::class.java)
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = PendingIntent.getBroadcast(context, Constants.REQ_CODE, intent, Constants.NO_FLAGS)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), millis, alarmIntent)
        // set cancelled flag.
        alarmCancelled = false
    }

    /**
     * Cancel the previously set UI refresh alarm.
     */
    internal fun cancelAlarm() {
        if(!alarmCancelled) {
            alarmManager.cancel(alarmIntent)
            alarmCancelled = true
        }
    }

    /**
     * Quick and dirty check alarm is cancelled.
     */
    internal fun isCancelled(): Boolean = alarmCancelled

    private var alarmCancelled = true
    private lateinit var alarmIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager
}