package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Set toolbar title.
        (activity?.id_toolbar as Toolbar).title = "Preferences"
        // Hide the bottom nav view.
        (activity?.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.GONE
        // Load the preferences from the XML resource.
        addPreferencesFromResource(R.xml.preferences)

        findPreference("key_maint_del_archived").onPreferenceClickListener = this

    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference!!.key) {
            "key_maint_del_archived" -> {
                val bp = ""
            }
        }

        return true
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {

        return true
    }
}