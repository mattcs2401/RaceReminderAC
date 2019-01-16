package mcssoft.com.racereminderac.ui.fragment.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import mcssoft.com.racereminderac.R

class RefreshFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_refresh)
    }
}