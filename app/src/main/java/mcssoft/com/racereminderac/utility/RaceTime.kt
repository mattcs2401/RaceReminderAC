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

    internal fun compareTo(time: String) : Int {
        val givenTime = timeToMillis(time)
        val currentTime = Calendar.getInstance(Locale.getDefault()).timeInMillis
        return currentTime.compareTo(givenTime)
    }

    /**
     * Check if the given time is a time from today.
     * @param givenInMillis The given time in mSec.
     * @return A value 0 if the given time is equal to the current time.
     *         A value < 0 if the current time is before the given time.
     *         A value > 0 if the current time is after the given time.
     */
    internal fun compareTo(givenInMillis: Long): Int {
        val now = Calendar.getInstance(Locale.getDefault())
        val given = Calendar.getInstance(Locale.getDefault())
        given.time = Date(givenInMillis)

        // TODO - check the comparison direction against the expected return values.
        return given.compareTo(now)
    }

    // Local constants.
    private val timeFormat24 = "kk:mm"
    private val dateFormat = "dd/MM/yyyy"
}
