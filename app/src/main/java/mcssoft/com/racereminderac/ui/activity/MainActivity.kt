package mcssoft.com.racereminderac.ui.activity

import android.os.Bundle
import android.util.Log
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
import mcssoft.com.racereminderac.ui.dialog.DeleteAllDialog
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.eventbus.DeleteAllMessage
import mcssoft.com.racereminderac.utility.eventbus.ManualRefreshMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), IRace.IRaceSelect, IRace.IRaceLongSelect,
        BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        RacePreferences.getInstance()?.preferencesCheck(this)

        initialise()

        Log.d("tag","MainActivity.onCreate()")
    }

    override fun onStart() {
        super.onStart()
//        EventBus.getDefault().register(this)
        Log.d("tag","MainActivity.onStart()")
    }

    override fun onStop() {
        super.onStop()
//        EventBus.getDefault().unregister(this)
        Log.d("tag","MainActivity.onStop()")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Interface - IRace">
    /**
     * From interface IRace.IRaceSelect
     */
    override fun onRaceSelect(id: Long) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

    /**
     *
     */
    override fun onRaceSelect(id: Long, sel0: String, sel1: String, sel2: String, sel3: String, multiSel: Boolean) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_UPDATE)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        bundle.putBoolean(getString(R.string.key_edit_existing_multi), multiSel)
        bundle.putStringArray(getString(R.string.key_edit_existing_multi_vals), arrayOf(sel0,sel1,sel2,sel3))
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
//            R.id.id_delete -> {
//                doDelete()
//            }
            R.id.id_refresh -> {
                EventBus.getDefault().post(ManualRefreshMessage())
            }
            R.id.id_settings -> {
                navController.navigate(R.id.preferencesFragment)
            }
            R.id.id_add -> {
                doAdd()
            }
        }
        return false
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    /**
     * Setup the UI components.
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

    /**
     * Bottom nav menu New (Add).
     */
    private fun doAdd() {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), Constants.EDIT_RACE_NEW)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

    /**
     * Bottom nav menu Delete.
     */
    private fun doDelete() {
        val dialog = DeleteAllDialog(this)
        dialog.show(supportFragmentManager, "delete_all_dialog")
    }
    //</editor-fold>

//    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
//    fun onMessageEvent(select: DeleteAllMessage) {
//        val bp = ""
//        //raceViewModel.deleteAll()
//    }

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
}