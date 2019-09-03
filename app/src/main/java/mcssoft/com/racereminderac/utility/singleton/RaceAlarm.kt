package mcssoft.com.racereminderac.utility.singleton

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.receiver.RaceAlarmReceiver
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

/**
 * Utility class as wrapper for AlarmManager.
 */
class RaceAlarm private constructor (private val context: Context) {

    companion object : SingletonBase<RaceAlarm, Context>(::RaceAlarm)
//    /**
//     * For Singleton instance.
//     * @Note: Can't use Context here, so context passed into the setAlarm() method.
//     */
//    companion object {
//        @Volatile private var instance: RaceAlarm? = null
//
//        fun getInstance(): RaceAlarm? {
//            if (instance == null) {
//                synchronized(RaceAlarm::class) {
//                    instance = RaceAlarm()
//                }
//            }
//            return instance
//        }
//    }

    /**
     * Set the UI refresh alarm.
     * @param interval: The alarm time in minutes.
     */
    internal fun setAlarm(interval: Long) {
        // Cancel any previously set alarm.
        cancelAlarm()
        // Set the interval equivalent in mSec.
        val millis = interval * 60 * 1000
        // Set the alarm manager and intent.
        val intent = Intent(context, RaceAlarmReceiver::class.java)
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = PendingIntent.getBroadcast(context, Constants.REQ_CODE, intent, Constants.NO_FLAGS)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), millis, alarmIntent)
        // Set cancelled flag.
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

    private var alarmCancelled = true
    private lateinit var alarmIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager
}