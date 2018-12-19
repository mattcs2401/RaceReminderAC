package mcssoft.com.racereminderac.utility

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * Utility wrapper class for the SharedPreferences.
 */
class RacePreferences {
    /**
     * For Singleton instance.
     * @Note: Can't use Context here, so context passed into the respective methods.
     */
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

    fun getDefaultRaceCode(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("key_race_code_pref", "R")
    }

    fun getDefaultCityCode(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("key_city_code_pref", "B")
    }

    /**
     * Check that default preference values exist.
     * @param context: Activity context.
     */
    fun preferencesCheck(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val map = sharedPrefs.all

        if(!map.contains("key_race_code_pref")) {
            sharedPrefs.edit().putString("key_race_code_pref", "R").apply()
        }

        if(!map.contains("key_city_code_pref")) {
            sharedPrefs.edit().putString("key_city_code_pref", "B").apply()
        }
    }

}