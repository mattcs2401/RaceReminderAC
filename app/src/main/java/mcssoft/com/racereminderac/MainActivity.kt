package mcssoft.com.racereminderac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.interfaces.IRaceSelect

class MainActivity : AppCompatActivity(), View.OnClickListener, IRaceSelect {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initialise()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_fab -> {
                bundle = Bundle()
                bundle.putString(getString(R.string.key_edit_type), getString(R.string.edit_type_new))
                navController.navigate(R.id.id_edit_fragment, bundle)
            }
        }
    }

    // From interface IRaceSelect.
    override fun onRaceSelect(race: Race) {
        bundle = Bundle()
        bundle.putString(getString(R.string.key_edit_type), getString(R.string.edit_type_existing))
        bundle.putParcelable(getString(R.string.key_edit_existing), race)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

//    private fun setupBottomNavMenu(navController: NavController) {
//        findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
//            NavigationUI.setupWithNavController(bottomNavView, navController)
//        }
//    }

    private fun initialise() {
        // Toolbar.
        setSupportActionBar(findViewById<Toolbar>(R.id.id_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // FAB
        fab = findViewById<FloatingActionButton>(R.id.id_fab)
        fab.setOnClickListener(this)

        // Navigation.
        navController = findNavController(this, R.id.id_nav_host_fragment)
        Navigation.setViewNavController(fab, navController)
    }

    private lateinit var bundle: Bundle
    private lateinit var fab: FloatingActionButton
    private lateinit var navController: NavController
}