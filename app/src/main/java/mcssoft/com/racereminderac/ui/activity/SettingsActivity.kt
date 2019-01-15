package mcssoft.com.racereminderac.ui.activity

import android.os.Bundle
import android.preference.PreferenceActivity
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.R.xml.preference_headers

class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

    }

    /**
     * Populate the activity with the top-level headers.
     */
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.preference_headers, target)
    }
}


