package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.preference.*
import kotlinx.android.synthetic.main.toolbar_base.*
import kotlinx.android.synthetic.main.main_activity.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.singleton.NetworkManager
import mcssoft.com.racereminderac.utility.singleton.RaceAlarm

class PreferencesFragment : PreferenceFragmentCompat(),Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()
//        Log.d("tag","PreferenceFragment.onViewCreated")
    }

    //<editor-fold default state="collapsed" desc="Region: Listeners">
    // TBA
    override fun onPreferenceClick(preference: Preference?): Boolean {

        val bp="bp"
        return  true
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        /** Note: This fires before onPreferenceClick(). **/
        when(preference.key) {
            activity?.resources?.getString(R.string.key_race_notif_send_pref) -> {
                doNotifySendPref(newValue)
            }
            activity?.resources?.getString(R.string.key_refresh_interval_pref) -> {
                doRefreshIntPref(newValue)
            }
            activity?.resources?.getString(R.string.key_refresh_interval_seek_pref) -> {
                doRefreshIntSeekPref(newValue)
            }
            activity?.resources?.getString(R.string.key_network_pref) -> {
                val bp="bp"
//                doNetworkPref(newValue)
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
        notify = findPreference(activity!!.resources.getString(R.string.key_race_notif_send_pref))
        notifyMulti = findPreference(activity!!.resources.getString(R.string.key_notif_send_multi_pref))
        refresh = findPreference(activity!!.resources.getString(R.string.key_refresh_interval_pref))
        refreshSeek = findPreference(activity!!.resources.getString(R.string.key_refresh_interval_seek_pref))
        network = findPreference(activity!!.resources.getString(R.string.key_network_pref))

        if(refresh?.isChecked!!) {
            refreshSeek?.isEnabled = true
        }

        // Set listeners.
        notify?.onPreferenceChangeListener = this
        refresh?.onPreferenceChangeListener = this
        refreshSeek?.onPreferenceChangeListener = this
        network?.onPreferenceChangeListener = this
        network?.onPreferenceClickListener = this
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

//    private fun doNetworkPref(newValue: Any) {
//        if(newValue == true) {
//            if(NetworkManager.getInstance(activity!!).isNetworkConnected()) {
//                when(NetworkManager.getInstance(activity!!).getTransport()) {
//                    Constants.NETWORK_MOB -> {
//                        val bp="bp"
//                    }
//                    Constants.NETWORK_WIFI -> {
//                        val bp="bp"
//                    }                        }
//            }
//        }
//    }
    //</editor-fold>

    private var notify: SwitchPreferenceCompat? = null          // post notifications.
    private var notifyMulti: SwitchPreferenceCompat? = null     // allow multi refresh same race.
    private var refresh: SwitchPreferenceCompat? = null         // refresh interval.
    private var refreshSeek: SeekBarPreference? = null          // refresh interval ammount.
    private var network: PreferenceCategory? = null         // network prefs.

    private var refreshVal: Int = Constants.REFRESH_MIN         // simply an initial value.
}