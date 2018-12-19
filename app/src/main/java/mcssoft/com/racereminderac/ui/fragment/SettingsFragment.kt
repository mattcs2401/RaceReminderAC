package mcssoft.com.racereminderac.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.background.worker.DAAWorker
import mcssoft.com.racereminderac.utility.RacePreferences

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

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

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val bp = ""
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference!!.key) {
            "key_race_code_pref" -> {

                val bp = ""
            }
            "key_maint_del_archived" -> {
                val daaWork = OneTimeWorkRequestBuilder<DAAWorker>().build()
                WorkManager.getInstance().enqueue(daaWork)

                Toast.makeText(activity, "Archived records removed", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when(preference!!.key) {
            "key_race_code_pref" -> {

                val bp = ""
            }
        }
        return true
    }

    private fun initialise() {
        racePrefs = RacePreferences.getInstance()!!
        // Set the preferences listeners.
        setListeners()
    }

    private fun setListeners() {
        findPreference("key_maint_del_archived").onPreferenceClickListener = this
        findPreference("key_maint_del_archived").onPreferenceChangeListener = this
        findPreference("key_race_code_pref").onPreferenceClickListener = this
        findPreference("key_race_code_pref").onPreferenceChangeListener = this
    }

    private lateinit var racePrefs: RacePreferences
}