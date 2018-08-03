package mcssoft.com.racereminderac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() { //}, View.OnClickListener {
// https://willowtreeapps.com/ideas/exploring-androids-navigation-architecture-component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.id_nav_host_fragment) as NavHostFragment? ?: return

        setupBottomNavMenu(host.navController)

    }

//    override fun onClick(view: View) {
//        when (view.id) {
//        }
//    }

    private fun setupBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navController)
        }
    }

}