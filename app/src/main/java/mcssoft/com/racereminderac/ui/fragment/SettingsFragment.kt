package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.preference.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.background.worker.DAAWorker
import org.greenrobot.eventbus.EventBus

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Set toolbar title.
        (activity?.id_toolbar as Toolbar).title = activity?.resources?.getString(R.string.preferences)

        // Hide the bottom navigation view.
        (activity?.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.GONE

        // Load the preferences from the XML resource.
        addPreferencesFromResource(R.xml.preferences)

        // Initialise (local variables).
        initialise()
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Preference listeners.">
    override fun onPreferenceClick(preference: Preference): Boolean {
        when(preference.key) {
           keyMaintDelArchvPref -> {
                val daaWork = OneTimeWorkRequestBuilder<DAAWorker>().build()
                WorkManager.getInstance().enqueue(daaWork)

                Toast.makeText(activity, archRemvlMsg, Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        /** Note: This fires before onPreferenceClick. **/
        when(preference.key) {
            // Race Code preference.
            keyRaceCodePref -> {
                Toast.makeText(activity, "$raceCodeMsg '$newValue'", Toast.LENGTH_SHORT).show()
                return true
            }
            // City Code preference.
            keyCityCodePref -> {
                Toast.makeText(activity, "$cityCodeMsg '$newValue'", Toast.LENGTH_SHORT).show()
                return true
            }
            // Notification send preference.
            keyNotifSendPref -> {
                val switch = findPreference<SwitchPreferenceCompat>(keyNotifMultiPref)
                if(newValue == true) {
                    switch.isEnabled = true
                } else {
                    switch.isChecked = false
                    switch.isEnabled = false
                }
                return true
            }
            keyRefreshInterval -> {
                val seek = findPreference<SeekBarPreference>(keyRefreshSeek)
                seek.isEnabled = newValue == true
                return true
            }
        }
        return false
    }
    //</editor-fold>

    private fun initialise() {
        /** Note:  Strings just for code readability. **/
        // Get the Toast message text.
        raceCodeMsg = activity!!.resources.getString(R.string.race_code_msg)
        cityCodeMsg = activity!!.resources.getString(R.string.city_code_msg)
        archRemvlMsg = activity!!.resources.getString(R.string.arch_removal_msg)

        // Get the keys to action preferences.
        keyRaceCodePref = activity!!.resources.getString(R.string.key_race_code_pref)
        keyCityCodePref = activity!!.resources.getString(R.string.key_city_code_pref)
        keyNotifSendPref = activity!!.resources.getString(R.string.key_race_notif_send_pref)
        keyNotifMultiPref = activity!!.resources.getString(R.string.key_notif_send_multi_pref)
        keyMaintDelArchvPref = activity!!.resources.getString(R.string.key_maint_del_archv_pref)
        keyRecoveryUndo = activity!!.resources.getString(R.string.key_recovery_undo_last_pref)
        keyRefreshInterval = activity!!.resources.getString(R.string.key_refresh_interval_pref)
        keyRefreshSeek = activity!!.resources.getString(R.string.key_refresh_interval_seek_pref)

        // Set the preferences listeners.
        (findPreference<Preference>(keyMaintDelArchvPref)).onPreferenceClickListener = this
        (findPreference<ListPreference>(keyCityCodePref)).onPreferenceChangeListener = this
        (findPreference<ListPreference>(keyRaceCodePref)).onPreferenceChangeListener = this
        (findPreference<SwitchPreferenceCompat>(keyNotifSendPref)).onPreferenceClickListener = this
        (findPreference<SwitchPreferenceCompat>(keyNotifSendPref)).onPreferenceChangeListener = this
        (findPreference<Preference>(keyRecoveryUndo)).onPreferenceClickListener = this
        (findPreference<SwitchPreferenceCompat>(keyRefreshInterval)).onPreferenceChangeListener = this
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private lateinit var raceCodeMsg: String
    private lateinit var cityCodeMsg: String
    private lateinit var archRemvlMsg: String
    private lateinit var keyRaceCodePref: String
    private lateinit var keyCityCodePref: String
    private lateinit var keyNotifSendPref: String
    private lateinit var keyNotifMultiPref: String
    private lateinit var keyMaintDelArchvPref: String
    private lateinit var keyRecoveryUndo: String
    private lateinit var keyRefreshInterval: String
    private lateinit var keyRefreshSeek: String
    //</editor-fold>
}