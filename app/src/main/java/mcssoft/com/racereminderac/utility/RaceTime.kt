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
     * @param time: The time formatted as "HH:MM" (String).
     * @return The time in milli seconds.
     */
    internal fun timeToMillis(@NonNull time: String) : Long {
        // Get hour/minute values.
        val timeVals = time.split(":")
        // Get local calendar.
        val calendar = Calendar.getInstance(Locale.getDefault())
        // Set calendar values.
        calendar.set(Calendar.HOUR_OF_DAY, timeVals[0].toInt());
        calendar.set(Calendar.MINUTE, timeVals[1].toInt());

        return calendar.getTimeInMillis()
    }

    /**
     * Compare the given time to the current time.
     * @param givenInMillis The given time in mSec.
     * @return A value 0 if the given time is equal to the current time.
     *         A value < 0 if the current time is before the given time, i.e. the given time is in the future.
     *         A value > 0 if the current time is after the given time, i.e. the given time is in the past.
     */
    internal fun compareToCurrent(givenInMillis: Long): Int {
        val now = Calendar.getInstance(Locale.getDefault())
        val given = Calendar.getInstance(Locale.getDefault())
        given.timeInMillis = givenInMillis

        return now.compareTo(given)
    }
    // https://developer.android.com/reference/java/util/Calendar.html#compareTo(java.util.Calendar)

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

    internal fun getCurrentTime() : Long {
        val calendar = Calendar.getInstance(Locale.getDefault())
        return calendar.timeInMillis
    }

    internal fun getWithinTimeWindow(priorTime: Long) : Boolean {

        return false
    }

    // Local constants.
    private val timeFormat24 = "kk:mm"
    private val dateFormat = "dd/MM/yyyy"
}
