package mcssoft.com.racereminderac.utility

import androidx.annotation.NonNull
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date

class RaceTime {

    companion object {
        val TIME: Int = 1
        val DATE: Int = 2

        fun getInstance(): RaceTime = RaceTime()
    }

    /**
     * Return the current date or time.
     * @param which: RaceTime.TIME for the current time, else RaceTime.DATE for the current date.
     */
    internal fun getFormattedDateTime(@NonNull which: Int) : String {
        lateinit var sdFormat: SimpleDateFormat
        val locale = Locale.getDefault()
        val calendar = Calendar.getInstance(locale)
        calendar.setTime(Date(calendar.getTimeInMillis()))

        when(which) {
            TIME -> {
                sdFormat = SimpleDateFormat(timeFormat24, locale)
            }
            DATE -> {
                sdFormat = SimpleDateFormat(dateFormat, locale)
            }
        }
        return sdFormat.format(calendar.getTime())
    }

    /**
     * Get the time in milli seconds.
     * @param hourOfDay: The hour of the day (24hr clock).
     * @param minute: The minute of the hour.
     * @return The time in milli seconds.
     */
    internal fun timeToMillis(hourOfDay: Int, minute: Int) : Long {
        // Get local calendar.
        val calendar = Calendar.getInstance(Locale.getDefault())
        // Set calendar values.
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis()
    }

    internal fun timeToMillis(time: String) : Long {
        // Get time hour/minute values.
        val sTime = time.split(":")
        // Get local calendar.
        val calendar = Calendar.getInstance(Locale.getDefault())
        // Set calendar values.
        calendar.set(Calendar.HOUR_OF_DAY, sTime[0].toInt());
        calendar.set(Calendar.MINUTE, sTime[1].toInt());

        return calendar.getTimeInMillis()
    }

    internal fun timeFromMillis(timeInMillis: Long): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        var hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
        var minute = calendar.get(Calendar.MINUTE).toString()

        if(hour.length < 2) hour = "0$hour"
        if(minute.length < 2) minute = "0$minute"

        return hour + ":" + minute
    }

    /**
     * Get a time value in mSec that is the given time, minus the prior time.
     * Example: given 08:00, prior 5 (minutes), result 07:55 (in mSec).
     * @param givenTime A time value in milli mSec.
     * @param priorTime A time value in minutes.
     * @return A time value that is the given time minus the prior time.
     */
    internal fun getTimePrior(givenTime: Long, priorTime: Int): Long {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.time = Date(givenTime)
        calendar.add(Calendar.MINUTE, -priorTime)
        return calendar.timeInMillis
    }

    internal fun getCurrentTime(): Long {
        val calendar = Calendar.getInstance(Locale.getDefault())
        return calendar.timeInMillis
    }

    /**
     * Compare the current time to that given.
     * @param givenTime: The time (in mSec) to compare against the current time.
     * @return -1: the current time is before that given.
     *          0: the current time is equal that given.
     *          1: the current time is after that given.
     */
    internal fun compareTo(givenTime: Long) : Int {
        var retVar: Int = 99
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = givenTime

        if(isBefore(calendar)) {
            retVar = -1
        } else if(isEqual(calendar)) {
            retVar = 0
        } else if(isAfter(calendar)) {
            retVar = 1
        }
        return retVar
    }

    private fun isBefore(cal: Calendar) : Boolean {
        val calendar = Calendar.getInstance(Locale.getDefault())
        var t = calendar.timeInMillis
        return calendar.before(cal)
    }

    private fun isAfter(cal: Calendar) : Boolean {
        val calendar = Calendar.getInstance(Locale.getDefault())
        return calendar.after(cal)
    }

    private fun isEqual(cal: Calendar): Boolean {
        val calendar = Calendar.getInstance(Locale.getDefault())
        var t = calendar.timeInMillis
        return calendar.equals(cal)
    }

    // Local constants.
    private val timeFormat24 = "kk:mm"
    private val dateFormat = "dd/MM/yyyy"
}
