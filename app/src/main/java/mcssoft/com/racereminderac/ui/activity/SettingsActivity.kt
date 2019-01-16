package mcssoft.com.racereminderac.ui.activity

import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.appcompat.app.AppCompatActivity
import mcssoft.com.racereminderac.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

    }

//    /**
//     * Populate the activity with the top-level headers.
//     */
//    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
//        loadHeadersFromResource(R.xml.preference_headers, target)
//    }

//    override fun isValidFragment(fragmentName: String?): Boolean {
//        return true //super.isValidFragment(fragmentName)
//    }
    // mcssoft.com.racereminderac.ui.fragment.settings.DefaultsFragment

}
