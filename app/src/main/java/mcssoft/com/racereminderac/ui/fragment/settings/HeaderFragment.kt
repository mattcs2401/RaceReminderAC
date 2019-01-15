package mcssoft.com.racereminderac.ui.fragment.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import mcssoft.com.racereminderac.R

class HeaderFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Load the preferences from the XML resource.
        addPreferencesFromResource(R.xml.preference_headers)

    }
}