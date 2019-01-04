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
        @Volatile private var instance: RaceTime? = null

        fun getInstance(): RaceTime? {
            if (instance == null) {
                synchronized(RaceTime::class) {
                    instance = RaceTime()
                }
            }
            return instance
        }
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
     * Compare the given date to today.
     * @param givenTime: The time to compare (the day is created from this).
     * @return 0: The given day (time) is before the current day.
     *         1: The given day (time) is the same as the current day.
     */
    internal fun compareToDay(givenTime: Long) : Int {
        // Establish calendars (initially the same).
        val calendarToday = Calendar.getInstance(Locale.getDefault())
        val calendarGiven = calendarToday

        // Get the Day associated with the given time.
        calendarGiven.timeInMillis = givenTime
        val givenDay = calendarGiven.get(Calendar.DAY_OF_YEAR)

        // Get the Day associated with today, i.e. the current time.
        val today = calendarToday.get(Calendar.DAY_OF_YEAR)

        // Compare the day of the given day with today.
        if(givenDay < today) {
            return Constants.DAY_PRIOR
        } else {
            return Constants.DAY_CURRENT
        }
    }

    /**
     * Compare the current time to that given.
     * @param givenTime: The time (in mSec) to compare against the current time.
     * @return -1: the current time is before that given.
     *          0: the current time is equal that given.
     *          1: the current time is after that given.
     */
    internal fun compareToTime(givenTime: Long) : Int {
        var retVar = 99
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = givenTime

        when {
            isBefore(calendar) -> retVar = Constants.CURRENT_TIME_BEFORE
            isAfter(calendar) -> retVar = Constants.CURRENT_TIME_AFTER
            isEqual(calendar) -> retVar = Constants.CURRENT_TIME_SAME
        }
        return retVar
    }

    /**
     * Wrapper for Calendar.isBefore()
     * @param calendar: The calendar to compare.
     */
    private fun isBefore(calendar: Calendar) : Boolean {
        return Calendar.getInstance(Locale.getDefault()).before(calendar)
    }

    /**
     * Wrapper for Calendar.isAfter()
     * @param calendar: The calendar to compare.
     */
    private fun isAfter(calendar: Calendar) : Boolean {
        return Calendar.getInstance(Locale.getDefault()).after(calendar)
    }

    /**
     * Wrapper for Calendar.isEqual()
     * @param calendar: The calendar to compare.
     */
    private fun isEqual(calendar: Calendar): Boolean {
        return Calendar.getInstance(Locale.getDefault()) == calendar
    }
}
