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

        return calendar.getTimeInMillis();
    }

    // Local constants.
    private val timeFormat24 = "kk:mm"
    private val dateFormat = "dd/MM/yyyy"
}
