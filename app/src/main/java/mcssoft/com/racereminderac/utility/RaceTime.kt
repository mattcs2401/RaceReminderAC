package mcssoft.com.racereminderac.utility

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date

//class RaceTime(context: Context) {
class RaceTime {

    companion object {
//        fun getInstance(context: Context): RaceTime = RaceTime(context)
        fun getInstance(): RaceTime = RaceTime()
    }

    /**
     * Get the time as a string formatted HH:MM.
     * @return The current time.
     */
    internal fun getFormattedTime(): String {
        val locale = Locale.getDefault()
        val calendar = Calendar.getInstance(locale)
        val sdFormat = SimpleDateFormat("kk:mm", locale)

        calendar.setTime(Date(calendar.getTimeInMillis()))
        return sdFormat.format(calendar.getTime())
    }

}