package mcssoft.com.racereminderac.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IRace

class MainActivity : AppCompatActivity(), View.OnClickListener, IRace.IRaceSelect {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initialise()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_fab -> {
                val bundle = Bundle()
                bundle.putInt(getString(R.string.key_edit_type), R.integer.edit_race_new)
                navController.navigate(R.id.id_edit_fragment, bundle)
            }
        }
    }

    // From interface IRace.
    override fun onRaceSelect(id: Long ) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), R.integer.edit_race_existing)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

//    private fun setupBottomNavMenu(navController: NavController) {
//        findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
//            NavigationUI.setupWithNavController(bottomNavView, navController)
//        }
//    }

    private fun initialise() {
        // Toolbar.
        setSupportActionBar(id_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // FAB
        id_fab.setOnClickListener(this)
        // Navigation.
        navController = findNavController(this, R.id.id_nav_host_fragment)
        Navigation.setViewNavController(id_fab, navController)
    }

    private lateinit var navController: NavController
}