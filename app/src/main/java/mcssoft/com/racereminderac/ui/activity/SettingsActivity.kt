package mcssoft.com.racereminderac.ui.activity

import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        initialise()
    }

    fun getNavController() : NavController {
        return navController
    }

    private fun initialise() {
        // Toolbar.
        setSupportActionBar(id_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Navigation.
        navController = Navigation.findNavController(this, R.id.id_nav_host_settings_fragment)

        // Back Navigation.
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    private lateinit var navController: NavController

}
