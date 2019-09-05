package mcssoft.com.racereminderac.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IRace
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.singleton.RacePreferences

class MainActivity : AppCompatActivity(R.layout.main_activity), IRace.IRaceSelect, IRace.IRaceLongSelect,
        BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RacePreferences.getInstance(this).preferencesCheck()

        initialise()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Interface - IRace">
    /**
     * From interface IRace.IRaceSelect
     * Navigate to the EditFragment to update Race details.
     * @param id: The race details row id in the database.
     */
    override fun onRaceSelect(id: Long) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

    /**
     * From interface IRace.IRaceSelect
     * Navigate to the EditFragment to update Race details when multi select enabled.
     * @param id: The race details row id in the database.
     * @param values: Additional multi select values.
     */
    override fun onRaceSelect(id: Long, values: Array<String>) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        bundle.putBoolean(getString(R.string.key_edit_existing_multi), true)
        bundle.putStringArray(getString(R.string.key_edit_existing_vals), values)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

    /**
     * From interface IRace.IRaceLongSelect
     * Navigate to the EditFragment to create Race details as a copy from another set of details.
     * @param id: The race details row id in the database.
     * @param values: Additional multi select values.
     */
    override fun onRaceLongSelect(id: Long, values: Array<String>) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_COPY)
        bundle.putLong(getString(R.string.key_edit_copy), id)
        bundle.putBoolean(getString(R.string.key_edit_existing_multi), true)
        bundle.putStringArray(getString(R.string.key_edit_existing_vals), values)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Bottom navigation listener">
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.id_mnu_bnv_settings -> {
                // Bottom navigation menu Settings.
                navController.navigate(R.id.preferencesFragment)
            }
            R.id.id_mnu_bnv_add -> {
                // Bottom navigation menu Add.
                val bundle = Bundle()
                bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_NEW)
                navController.navigate(R.id.id_edit_fragment, bundle)
            }
        }
        return false
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Utility">
    /**
     * Setup the action bar and navigation.
     */
    private fun initialise() {
        // Toolbar.
        setSupportActionBar(id_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Navigation.
        navController = findNavController(this, R.id.id_nav_host_fragment)
        bottomNavView = findViewById(R.id.id_bottom_nav_view)
        NavigationUI.setupWithNavController(bottomNavView, navController)
        bottomNavView.setOnNavigationItemSelectedListener(this)

        // Back Navigation.
        setupActionBarWithNavController(this, navController)
    }
    //</editor-fold>

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
}