package mcssoft.com.racereminderac.utility

import android.content.Context
import android.preference.PreferenceManager

/**
 * Utility wrapper class for the SharedPreferences.
 */
class RacePreferences {

    companion object {
        @Volatile private var instance: RacePreferences? = null

        fun getInstance(): RacePreferences? {
            if (instance == null) {
                synchronized(RacePreferences::class) {
                    instance = RacePreferences()
                }
            }
            return instance
        }
    }

    fun preferencesCheck(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val map = sharedPrefs.all

        if(!map.contains("key_race_code_pref")) {
            sharedPrefs.edit().putString("key_race_code_pref", "R").apply()
        }
    }

}