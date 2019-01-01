package mcssoft.com.racereminderac.utility

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
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
     * Get the (default) Race Code from the preferences.
     * @param context: Activity context.
     */
    fun getRaceCode(context: Context): String? {
        val key = context.resources.getString(R.string.key_race_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * Get the (default) City Code from the preferences.
     * @param context: Activity context.
     */
    fun getCityCode(context: Context): String? {
        val key = context.resources.getString(R.string.key_city_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * Get the (allowed to) post notifications from the preferences.
     * @param context: Activity context.
     */
    fun getRaceNotifPost(context: Context) : Boolean {
        val keyNotif = context.resources.getString(R.string.key_race_notif_send_pref)
        val state = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(keyNotif, false)
        return state
    }

    /**
     * Get the (allowed to) post multiple notifications for the same Race from the preferences.
     * @param context: Activity context.
     */
    fun getRaceNotifMulti(context: Context) : Boolean {
        val key = context.resources.getString(R.string.key_notif_send_multi_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Check that default preference values exist, and if not, then set them.
     * @param context: Activity context.
     */
    fun preferencesCheck(context: Context) {
        val keyRaceCode = context.resources.getString(R.string.key_race_code_pref)
        val keyCityCode = context.resources.getString(R.string.key_city_code_pref)
        val keyRaceNotifSend = context.resources.getString(R.string.key_race_notif_send_pref)
        val keyRaceNotifMulti = context.resources.getString(R.string.key_notif_send_multi_pref)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val map = sharedPrefs.all

        if(!map.contains(keyRaceCode)) {
            sharedPrefs.edit().putString(keyRaceCode, "R").apply()
        }

        if(!map.contains(keyCityCode)) {
            sharedPrefs.edit().putString(keyCityCode, "B").apply()
        }

        if(!map.contains(keyRaceNotifSend)) {
            sharedPrefs.edit().putBoolean(keyRaceNotifSend, false).apply()
        }

        if(!map.contains(keyRaceNotifMulti)) {
            sharedPrefs.edit().putBoolean(keyRaceNotifMulti, false).apply()
        }
    }

}