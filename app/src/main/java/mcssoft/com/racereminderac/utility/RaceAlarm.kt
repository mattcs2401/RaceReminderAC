package mcssoft.com.racereminderac.utility

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

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
        val intent = Intent(context, RaceReceiver::class.java)
        alarmIntent = PendingIntent.getBroadcast(
                context, 123, intent, 0)

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, alarmIntent)
    }

    internal fun cancelAlarm() {
//        if(alarmManager != null) {
            alarmManager.cancel(alarmIntent)
//        }
    }

    private lateinit var alarmIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager
}