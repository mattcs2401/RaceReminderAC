package mcssoft.com.racereminderac.utility

import androidx.annotation.NonNull
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date

/**
 * Utility class to manipulate date and time values.
 */
class RaceTime {

    companion object {
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
        calendar.time = Date(calendar.timeInMillis)

        when(which) {
            Constants.TIME -> {
                sdFormat = SimpleDateFormat(Constants.TIME_FORMAT_24, locale)
            }
            Constants.DATE -> {
                sdFormat = SimpleDateFormat(Constants.DATE_FORMAT, locale)
            }
        }
        return sdFormat.format(calendar.time)
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
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        return calendar.timeInMillis
    }

    /**
     * Get the time from the parameter.
     * @param timeInMillis: The time value in mSec.
     * @return The time as HH:MM.
     */
    internal fun timeFromMillis(timeInMillis: Long): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        var hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
        var minute = calendar.get(Calendar.MINUTE).toString()

        if(hour.length < 2) hour = "0$hour"
        if(minute.length < 2) minute = "0$minute"

        return "$hour:$minute"
    }

    /**
     * Compare the current time to that given.
     * @param givenTime: The time (in mSec) to compare against the current time.
     * @return -1: the current time is before that given.
     *          0: the current time is equal that given.
     *          1: the current time is after that given.
     */
    internal fun compareTo(givenTime: Long) : Int {
        var retVar = 99
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = givenTime

        if(isBefore(calendar)) {
            retVar = Constants.CURRENT_TIME_BEFORE
        } else if(isAfter(calendar)) {
            retVar = Constants.CURRENT_TIME_AFTER
        } else if(isEqual(calendar)) {
            retVar = Constants.CURRENT_TIME_SAME
        }
        return retVar
    }

    /**
     * Wrapper for Calendar.isBefore()
     * @param cal: The calendar to compare.
     */
    private fun isBefore(cal: Calendar) : Boolean {
        return Calendar.getInstance(Locale.getDefault()).before(cal)
    }

    /**
     * Wrapper for Calendar.isAfter()
     * @param cal: The calendar to compare.
     */
    private fun isAfter(cal: Calendar) : Boolean {
        return Calendar.getInstance(Locale.getDefault()).after(cal)
    }

    /**
     * Wrapper for Calendar.isEqual()
     * @param cal: The calendar to compare.
     */
    private fun isEqual(cal: Calendar): Boolean {
        return Calendar.getInstance(Locale.getDefault()) == cal
    }
}
