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
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.eventbus.ManualRefreshMessage
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity(), IRace.IRaceSelect, IRace.IRaceLongSelect,
        BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        RacePreferences.getInstance()?.preferencesCheck(this)

        initialise()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Interface - IRace">
    /**
     * From interface IRace.IRaceSelect
     */
    override fun onRaceSelect(id: Long ) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

    /**
     * From interface IRace.IRaceLongSelect
     */
    override fun onRaceLongSelect(id: Long) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_COPY)
        bundle.putLong(getString(R.string.key_edit_copy), id)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Bottom navigation listener">
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.id_refresh -> {
                EventBus.getDefault().post(ManualRefreshMessage())
            }
            R.id.id_settings -> {
                navController.navigate(R.id.preferencesFragment)
            }
            R.id.id_add -> {
                val bundle = Bundle()
                bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_NEW)
                navController.navigate(R.id.id_edit_fragment, bundle)
            }
        }
        return false
    }
    //</editor-fold>

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

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
}