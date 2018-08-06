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
        val fabAdd: FloatingActionButton = findViewById<FloatingActionButton>(R.id.id_fab)
        Navigation.setViewNavController(fabAdd, navController)

        fabAdd.setOnClickListener {
            navController.navigate(R.id.id_edit_fragment)
        }
    }

}
/*
val navController = findNavController(R.id.navHostFragment)
Navigation.setViewNavController(fabAdd, navController)
fabAdd.setOnClickListener {
navController.navigate(R.id.addSymbolFragment)
}
*/