package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.*
import kotlinx.android.synthetic.main.toolbar_base.*
import kotlinx.android.synthetic.main.main_activity.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.singleton.RaceAlarm

class PreferencesFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
//Preference.OnPreferenceClickListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the preference values and listeners.
        initialise()
    }

    //<editor-fold default state="collapsed" desc="Region: Listeners">
//    override fun onPreferenceClick(preference: Preference?): Boolean {
//        return  true
//    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        /** Note: This fires before onPreferenceClick(). **/
        when(preference.key) {
            getString(R.string.key_city_code_pref) -> {
                Toast.makeText(activity,
                        """${getString(R.string.default_city_code_msg)} $newValue""", Toast.LENGTH_SHORT).show()
            }
            getString(R.string.key_race_code_pref) -> {
                Toast.makeText(activity,
                        """${getString(R.string.default_race_code_msg)} $newValue""", Toast.LENGTH_SHORT).show()
            }
            getString(R.string.key_race_notif_send_pref) -> {
                doNotifySendPref(newValue)
            }
            getString(R.string.key_refresh_interval_pref) -> {
                doRefreshIntPref(newValue)
            }
            getString(R.string.key_refresh_interval_seek_pref) -> {
                doRefreshIntSeekPref(newValue)
            }
            getString(R.string.key_network_enable) -> {
                doNetworkEnable(newValue)
            }
        }
        return true
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Utility">
    private fun initialise() {
        // Hide the bottom nav view and set the screen title.
        activity?.id_bottom_nav_view?.visibility = View.GONE
        activity?.id_toolbar?.title = getString(R.string.preferences)

        // Get preferences.
        raceCode= findPreference(activity!!.resources.getString(R.string.key_race_code_pref))
        cityCode= findPreference(activity!!.resources.getString(R.string.key_city_code_pref))
        notify = findPreference(activity!!.resources.getString(R.string.key_race_notif_send_pref))
        notifyMulti = findPreference(activity!!.resources.getString(R.string.key_notif_send_multi_pref))
        refresh = findPreference(activity!!.resources.getString(R.string.key_refresh_interval_pref))
        refreshSeek = findPreference(activity!!.resources.getString(R.string.key_refresh_interval_seek_pref))
        network = findPreference(activity!!.resources.getString(R.string.key_network_enable))
        networkType = findPreference(activity!!.resources.getString(R.string.key_network_type_pref))

        if(refresh?.isChecked!!) {
            refreshSeek?.isEnabled = true
        }

        // Set listeners.
        raceCode?.onPreferenceChangeListener = this
        cityCode?.onPreferenceChangeListener = this
        notify?.onPreferenceChangeListener = this
        refresh?.onPreferenceChangeListener = this
        refreshSeek?.onPreferenceChangeListener = this
        network?.onPreferenceChangeListener = this
    }

    private fun doNotifySendPref(newValue: Any) {
        if(newValue == false) {
            notifyMulti?.isChecked = false
            notifyMulti?.isEnabled = false
        } else {
            notifyMulti?.isEnabled = true
        }
    }

    private fun doRefreshIntPref(newValue: Any) {
        if(newValue == false) {
            refreshSeek?.isEnabled = false
            RaceAlarm.getInstance(activity!!).cancelAlarm()
        } else {
            refreshSeek?.isEnabled = true
            if(refreshSeek?.value == 0) {
                refreshSeek?.value = Constants.REFRESH_DEFAULT
            }
            RaceAlarm.getInstance(activity!!).setAlarm(refreshSeek?.value!!.toLong())
        }
    }

    private fun doRefreshIntSeekPref(newValue: Any) {
        refreshVal = newValue as Int
        if(refreshVal > 0) {
            refreshSeek?.value = refreshVal
            RaceAlarm.getInstance(activity!!).cancelAlarm()
            RaceAlarm.getInstance(activity!!).setAlarm(refreshVal.toLong())
        } else {
            refresh?.isChecked = false
            refreshSeek?.isEnabled = false
            RaceAlarm.getInstance(activity!!).cancelAlarm()
        }
    }

    private fun doNetworkEnable(newValue: Any) {
        networkType?.isEnabled = newValue == true
    }
    //</editor-fold>

    private var cityCode: ListPreference? = null                //
    private var raceCode: ListPreference? = null                //
    private var notify: SwitchPreferenceCompat? = null          // post notifications.
    private var notifyMulti: SwitchPreferenceCompat? = null     // allow multi refresh same race.
    private var refresh: SwitchPreferenceCompat? = null         // refresh interval.
    private var refreshSeek: SeekBarPreference? = null          // refresh interval ammount.
    private var network: SwitchPreferenceCompat? = null         // network enable download.
    private var networkType: ListPreference? = null             // network type selection.

    private var refreshVal: Int = Constants.REFRESH_MIN         // simply an initial value.
}