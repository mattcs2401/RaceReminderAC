package mcssoft.com.racereminderac.utility

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Utility wrapper class for the SharedPreferences.
 */
class RacePreferences {

    companion object {

        @Volatile private var instance: SharedPreferences? = null

        fun getInstance(context: Context): SharedPreferences? {
            if (instance == null) {
                synchronized(RacePreferences::class) {
                    instance = PreferenceManager.getDefaultSharedPreferences(context)
                }
            }
            return instance
        }
    }

}