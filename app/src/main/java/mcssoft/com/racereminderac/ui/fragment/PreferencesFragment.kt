package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R

class PreferencesFragment : PreferenceFragmentCompat(),Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hide the bottom nav view.
        activity?.findViewById<BottomNavigationView>(R.id.id_bottom_nav_view)?.visibility = View.GONE
        (activity?.id_toolbar)?.title = getString(R.string.preferences)
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        // TODO - onPreferenceClick
        return true
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        // TODO - onPreferenceChange
        return true
    }


}