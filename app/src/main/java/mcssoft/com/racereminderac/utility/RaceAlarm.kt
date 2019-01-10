package mcssoft.com.racereminderac.utility

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

/**
 * Utility class as wrapper for AlarmManager.
 * @Note: Listens for changes in the Refresh Interval preference.
 */
class RaceAlarm {

    /**
     * For Singleton instance.
     * @Note: Can't use Context here, so context passed into the respective methods as required.
     */
    companion object {
        @Volatile private var instance: RaceAlarm? = null

        fun getInstance(): RaceAlarm? {
            if (instance == null) {
                synchronized(RacePreferences::class) {
                    instance = RaceAlarm()
                }
            }
            return instance
        }
    }

    internal fun setAlarm(context: Context) {
        // Get the switch preference that enables the seek slider.
        if(RacePreferences.getInstance()!!.getRefreshInterval(context)) {
            var interval = RacePreferences.getInstance()!!.getRefreshIntervalVal(context).toLong()
            if(interval > 0) {
                interval *= 60 * 1000
                val intent = Intent(context, RaceReceiver::class.java)
                alarmIntent = PendingIntent.getBroadcast(
                        context, Constants.REQ_CODE, intent, Constants.NO_FLAGS)

                alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, alarmIntent)
            }
        }
    }

    internal fun cancelAlarm() {
        // Alarm manager may not have been initialsied depending on the conditions in setAlarm().
        if(alarmManager != null) {
            alarmManager!!.cancel(alarmIntent)
        }
    }

    private var alarmIntent: PendingIntent? = null
    private var alarmManager: AlarmManager? = null
}