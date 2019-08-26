package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants

/**
 * Utility wrapper class for the SharedPreferences.
 */
class RacePreferences {
    /**
     * For Singleton instance.
     * @Note: Can't use Context here, so context passed into the respective methods as required.
     */
    companion object {
        @Volatile
        private var INSTANCE: RacePreferences? = null

        fun getInstance() =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: RacePreferences().also {
                        INSTANCE = it
                    }

                }
    }

    /**
     * Get the (default) Race Code from the preferences.
     * @param context: Activity context.
     * @return The Race Code value which is the default value for any Race entry.
     */
    fun getRaceCode(context: Context): String? {
        val key = context.resources.getString(R.string.key_race_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * Get the (default) City Code from the preferences.
     * @param context: Activity context.
     * @return The City Code value which is the default value for any Race entry.
     */
    fun getCityCode(context: Context): String? {
        val key = context.resources.getString(R.string.key_city_code_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * Get the (allowed to) post notifications from the preferences.
     * @param context: Activity context.
     * @return True if preference is enabled, else false
     */
    fun getRaceNotifPost(context: Context) : Boolean {
        val keyNotif = context.resources.getString(R.string.key_race_notif_send_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(keyNotif, false)
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
     * Allow recovery (Undo) of the last item deleted.
     * @param context: Activity context.
     * @return True if preference is enabled, else false
     */
    fun getRecoveryUndoLast(context: Context) : Boolean {
        val key = context.resources.getString(R.string.key_recovery_undo_last_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Refresh interval switch preference.
     * @param context: Activity context.
     * @return True if preference is enabled, else false.
     */
    fun getRefreshInterval(context: Context) : Boolean {
        val key = context.resources.getString(R.string.key_refresh_interval_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the value to be used as the refresh interval.
     * @param context: Activity context.
     * @return Refresh interval value.
     */
    fun getRefreshIntervalVal(context: Context) : Int {
        val key = context.resources.getString(R.string.key_refresh_interval_seek_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0)
    }

    /**
     * Get the Multi Select switch preference.
     * @param context: Activity context.
     * @return True if preference is enabled, else false.
     */
    fun getRaceMultiSelect(context: Context) : Boolean {
        val key = context.resources.getString(R.string.key_multi_select_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Bulk Delete switch preference.
     * @param context: Activity context.
     * @return True if preference is enabled, else false.
     */
    fun getRaceBulkDelete(context: Context) : Boolean {
        val key = context.resources.getString(R.string.key_bulk_delete_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Network switch preference.
     * @param context: Activity context.
     * @return True if preference is enabled, else false.
     */
    fun getNetwork(context: Context) : Boolean {
        val key = context.resources.getString(R.string.key_network_pref)
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
    }

    /**
     * Get the Network switch preference.
     * @param context: Activity context.
     * @return True if preference is enabled, else false.
     */
    fun getNetworkVal(context: Context) : Int {
        val key = context.resources.getString(R.string.key_network_pref_type)
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, -1)
    }

    /**
     * Check that default preference values exist, and if not, then set them.
     * @param context: Activity context.
     */
    fun preferencesCheck(context: Context) {
        // String values mainly just for readability.
        val keyRaceCode = context.resources.getString(R.string.key_race_code_pref)
        val keyCityCode = context.resources.getString(R.string.key_city_code_pref)
        val keyRaceNotifSend = context.resources.getString(R.string.key_race_notif_send_pref)
        val keyRaceNotifMulti = context.resources.getString(R.string.key_notif_send_multi_pref)
        val keyRecoveryUndoLast = context.resources.getString(R.string.key_recovery_undo_last_pref)
        val keyRefreshInterval = context.resources.getString(R.string.key_refresh_interval_pref)
        val keyMultiSelect = context.resources.getString(R.string.key_multi_select_pref)
        val keyBulkDelete = context.resources.getString(R.string.key_bulk_delete_pref)
        val keyNetworkPref = context.resources.getString(R.string.key_network_pref)
        val keyNetworkPrefType = context.resources.getString(R.string.key_network_pref_type)

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

        if(!map.contains(keyNetworkPrefType)) {
            // Default network type is both.
            sharedPrefs.edit().putInt(keyNetworkPrefType, Constants.NETWORK_EITHER).apply()
        }

        Log.d("tag","RacePreferences.preferenceCheck")
    }

}