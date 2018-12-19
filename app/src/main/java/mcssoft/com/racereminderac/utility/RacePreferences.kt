package mcssoft.com.racereminderac.utility

import android.content.Context
import androidx.preference.PreferenceManager
import mcssoft.com.racereminderac.R

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

    /**
     * Get the default Race Code from the preferences.
     * @param context: Activity context.
     */
    fun getDefaultRaceCode(context: Context): String? {
        val key = context.resources.getString(R.string.key_race_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null) //"R")
    }

    /**
     * Get the default City Code from the preferences.
     * @param context: Activity context.
     */
    fun getDefaultCityCode(context: Context): String? {
        val key = context.resources.getString(R.string.key_city_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null) //"B")
    }

    /**
     * Check that default preference values exist, and if not, then set them.
     * @param context: Activity context.
     */
    fun preferencesCheck(context: Context) {
        val keyRaceCode = context.resources.getString(R.string.key_race_code_pref)
        val keyCityCode = context.resources.getString(R.string.key_city_code_pref)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val map = sharedPrefs.all

        if(!map.contains(keyRaceCode)) {
            sharedPrefs.edit().putString(keyRaceCode, "R").apply()
        }

        if(!map.contains(keyCityCode)) {
            sharedPrefs.edit().putString(keyCityCode, "B").apply()
        }
    }

}