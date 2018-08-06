package mcssoft.com.racereminderac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navController = findNavController(R.id.id_nav_host_fragment)
        val fab = findViewById<FloatingActionButton>(R.id.id_fab)
        Navigation.setViewNavController(fab, navController)

        fab.setOnClickListener {
            navController.navigate(R.id.id_edit_fragment)
        }
    }

//    private fun setupBottomNavMenu(navController: NavController) {
//        findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
//            NavigationUI.setupWithNavController(bottomNavView, navController)
//        }
//    }

}