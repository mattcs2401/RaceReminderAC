package mcssoft.com.racereminderac.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import mcssoft.com.racereminderac.R

class Settings : PreferenceFragmentCompat() {

    companion object {
        fun getInstance(): Settings = Settings()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        // Load the preferences from the XML resource.
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

}