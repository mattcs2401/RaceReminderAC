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
                sdFormat = SimpleDateFormat("kk:mm", locale)
            }
            DATE -> {
                sdFormat = SimpleDateFormat("dd/MM/yyyy", locale)
            }
        }
        return sdFormat.format(calendar.getTime())
    }
}
