package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import android.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import mcssoft.com.racereminderac.R

class Settings : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    companion object {
        fun getInstance(): Settings = Settings()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        // Load the preferences from the XML resource.
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {

        return true
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {

        return true
    }
}