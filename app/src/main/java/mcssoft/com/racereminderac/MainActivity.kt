package mcssoft.com.racereminderac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mcssoft.com.racereminderac.interfaces.ICodes
import mcssoft.com.racereminderac.interfaces.IRace

class MainActivity : AppCompatActivity(), View.OnClickListener,
        IRace.IRaceSelect, ICodes.ICityCodes, ICodes.IRaceCodes {

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

    // From interface IRace.
    override fun onRaceSelect(id: Long ) { //race: Race) {
        bundle = Bundle()
        bundle.putString(getString(R.string.key_edit_type), getString(R.string.edit_type_existing))
//        bundle.putParcelable(getString(R.string.key_edit_existing), race)
        bundle.putLong(getString(R.string.key_edit_existing), id)
        navController.navigate(R.id.id_edit_fragment, bundle)
    }

//    private fun setupBottomNavMenu(navController: NavController) {
//        findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
//            NavigationUI.setupWithNavController(bottomNavView, navController)
//        }
//    }

    override fun onFinishCityCodes(code: String) {
        val bp = ""
    }

    override fun onFinishRaceCodes(code: String) {
        val bp = ""
    }

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