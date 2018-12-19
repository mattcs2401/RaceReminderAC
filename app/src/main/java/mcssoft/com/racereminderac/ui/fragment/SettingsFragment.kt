package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.background.worker.DAAWorker

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Set toolbar title.
        (activity?.id_toolbar as Toolbar).title = activity?.resources?.getString(R.string.preferences)
        // Hide the bottom nav view.
        (activity?.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.GONE
        // Load the preferences from the XML resource.
        addPreferencesFromResource(R.xml.preferences)
        // Initialise.
        initialise()
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference!!.key) {
           keyMaintDelArchived -> {
                val daaWork = OneTimeWorkRequestBuilder<DAAWorker>().build()
                WorkManager.getInstance().enqueue(daaWork)

                Toast.makeText(activity, archRemvlMsg, Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when(preference!!.key) {
            keyRaceCodePref -> {
                Toast.makeText(activity, "$raceCodeMsg '$newValue'", Toast.LENGTH_SHORT).show()
                return true
            }
            keyCityCodePref -> {
                Toast.makeText(activity, "$cityCodeMsg '$newValue'", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    private fun initialise() {
        // Strings mainly for code readability.
        // Get Toast message text.
        raceCodeMsg = activity!!.resources.getString(R.string.race_code_msg)
        cityCodeMsg = activity!!.resources.getString(R.string.city_code_msg)
        archRemvlMsg = activity!!.resources.getString(R.string.arch_removal_msg)
        // Get the keys to action preferences.
        keyMaintDelArchived = activity!!.resources.getString(R.string.key_maint_del_archv)
        keyRaceCodePref = activity!!.resources.getString(R.string.key_race_code_pref)
        keyCityCodePref = activity!!.resources.getString(R.string.key_city_code_pref)
        // Set the preferences listeners.
        findPreference(keyMaintDelArchived).onPreferenceClickListener = this
        findPreference(keyCityCodePref).onPreferenceChangeListener = this
        findPreference(keyRaceCodePref).onPreferenceChangeListener = this
    }

    private lateinit var keyMaintDelArchived: String
    private lateinit var keyRaceCodePref: String
    private lateinit var keyCityCodePref: String
    private lateinit var raceCodeMsg: String
    private lateinit var cityCodeMsg: String
    private lateinit var archRemvlMsg: String
}