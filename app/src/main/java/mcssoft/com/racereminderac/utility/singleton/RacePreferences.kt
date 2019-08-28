package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import androidx.preference.PreferenceManager
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

/**
 * Utility wrapper class for the SharedPreferences.
 */
class RacePreferences private constructor (private val context: Context) {

    companion object : SingletonBase<RacePreferences, Context>(::RacePreferences)

    /**
     * Get the (default) Race Code from the preferences.
     * @return The Race Code value which is the default value for any Race entry.
     */
    fun getRaceCode(): String? {
        val key = context.resources.getString(R.string.key_race_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * Get the (default) City Code from the preferences.
     * @return The City Code value which is the default value for any Race entry.
     */
    fun getCityCode(): String? {
        val key = context.resources.getString(R.string.key_city_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * Get the (allowed to) post notifications from the preferences.
     * @return True if preference is enabled, else false
     */
    fun getRaceNotifyPost() : Boolean {
        val key = context.resources.getString(R.string.key_race_notif_send_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the (allowed to) post multiple notifications for the same Race from the preferences.
     * @return True if post multiple notifications enabled, else false.
     */
    fun getRaceNotifyMulti() : Boolean {
        val key = context.resources.getString(R.string.key_notif_send_multi_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Allow recovery (Undo) of the last item deleted.
     * @return True if preference is enabled, else false
     */
    fun getRecoveryUndoLast() : Boolean {
        val key = context.resources.getString(R.string.key_recovery_undo_last_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Refresh interval switch preference.
     * @return True if preference is enabled, else false.
     */
    fun getRefreshInterval() : Boolean {
        val key = context.resources.getString(R.string.key_refresh_interval_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the value to be used as the refresh interval.
     * @return Refresh interval value.
     */
    fun getRefreshIntervalVal() : Int {
        val key = context.resources.getString(R.string.key_refresh_interval_seek_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0)
    }

    /**
     * Get the Multi Select switch preference.
     * @return True if preference is enabled, else false.
     */
    fun getRaceMultiSelect() : Boolean {
        val key = context.resources.getString(R.string.key_multi_select_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Bulk Delete switch preference.
     * @return True if preference is enabled, else false.
     */
    fun getRaceBulkDelete() : Boolean {
        val key = context.resources.getString(R.string.key_bulk_delete_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    fun getNetworkEnable() : Boolean {
        val key = context.resources.getString(R.string.key_network_enable)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Network preference.
     * @return The current network type preference.
     */
    fun getNetworkTypePref() : String? {
        val key = context.resources.getString(R.string.key_network_type_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "2")
    }

    /**
     * Check that default preference values exist, and if not, then set them.
     */
    fun preferencesCheck() {
        // String values mainly just for readability.
        val keyRaceCode = context.resources.getString(R.string.key_race_code_pref)
        val keyCityCode = context.resources.getString(R.string.key_city_code_pref)
        val keyRaceNotifSend = context.resources.getString(R.string.key_race_notif_send_pref)
        val keyRaceNotifMulti = context.resources.getString(R.string.key_notif_send_multi_pref)
        val keyRecoveryUndoLast = context.resources.getString(R.string.key_recovery_undo_last_pref)
        val keyRefreshInterval = context.resources.getString(R.string.key_refresh_interval_pref)
        val keyMultiSelect = context.resources.getString(R.string.key_multi_select_pref)
        val keyBulkDelete = context.resources.getString(R.string.key_bulk_delete_pref)
        val keyNetworkPref= context.resources.getString(R.string.key_network_enable)
        val keyNetworkTypePref = context.resources.getString(R.string.key_network_type_pref)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val map = sharedPrefs.all

        // If SharedPreferences don't exist, then set them with their defaults.
        if(!map.contains(keyCityCode)) {
            sharedPrefs.edit().putString(keyCityCode, "B").apply()
        }

        if(!map.contains(keyRaceCode)) {
            sharedPrefs.edit().putString(keyRaceCode, "R").apply()
        }

        if(!map.contains(keyRaceNotifSend)) {
            sharedPrefs.edit().putBoolean(keyRaceNotifSend, false).apply()
        }

        if(!map.contains(keyRaceNotifMulti)) {
            sharedPrefs.edit().putBoolean(keyRaceNotifMulti, false).apply()
        }

        if(!map.contains(keyRecoveryUndoLast)) {
            sharedPrefs.edit().putBoolean(keyRecoveryUndoLast, false).apply()
        }

        if(!map.contains(keyRefreshInterval)) {
            sharedPrefs.edit().putBoolean(keyRefreshInterval, false).apply()
        }

        if(!map.contains(keyMultiSelect)) {
            sharedPrefs.edit().putBoolean(keyMultiSelect, false).apply()
        }

        if(!map.contains(keyBulkDelete)) {
            sharedPrefs.edit().putBoolean(keyBulkDelete, false).apply()
        }

        if(!map.contains(keyNetworkPref)) {
            sharedPrefs.edit().putBoolean(keyNetworkPref, false).apply()
        }

        if(!map.contains(keyNetworkTypePref)) {
            sharedPrefs.edit().putString(keyNetworkTypePref, "2").apply()
        }
//        Log.d("tag","RacePreferences.preferenceCheck")
    }
}