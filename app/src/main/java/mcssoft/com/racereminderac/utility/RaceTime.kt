package mcssoft.com.racereminderac.utility

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.Date

class RaceTime(context: Context) {

    companion object {
        fun getInstance(context: Context): RaceTime = RaceTime(context)
    }

    /**
     * Get the time as a string formatted HH:MM.
     * @return The current time.
     */
    fun getFormattedTime(): String {
        val time: String

        val locale = Locale.getDefault()
        val calendar = Calendar.getInstance(locale)
//        val timeInMillis = calendar.getTimeInMillis()
        val sdFormat: SimpleDateFormat = SimpleDateFormat("kk:mm", locale)

        calendar.setTime(Date(calendar.getTimeInMillis()))
        time = sdFormat.format(calendar.getTime())

        return time
    }

}