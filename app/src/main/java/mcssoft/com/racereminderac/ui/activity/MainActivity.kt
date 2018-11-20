package mcssoft.com.racereminderac.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.work.WorkManager
import androidx.work.WorkInfo
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
                bundle.putInt(getString(R.string.key_edit_type), resources.getInteger(R.integer.edit_race_new))
                navController.navigate(R.id.id_edit_fragment2, bundle)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * From interface IRace.IRaceSelect
     */
    override fun onRaceSelect(id: Long ) {
        val bundle = Bundle()
        bundle.putInt(getString(R.string.key_edit_type), resources.getInteger(R.integer.edit_race_existing))
        bundle.putLong(getString(R.string.key_edit_existing), id)
        navController.navigate(R.id.id_edit_fragment2, bundle)
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
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // FAB
        id_fab.setOnClickListener(this)
        // Navigation.
        navController = findNavController(this, R.id.id_nav_host_fragment)
        Navigation.setViewNavController(id_fab, navController)
        // Back Navigation.
        setupActionBarWithNavController(this, navController)

//        WorkManager.getInstance().getStatusById(simpleRequest.getId())
//                .observe(this, object : Observer<WorkStatus> {
//                    override fun onChanged(@Nullable workStatus: WorkStatus?) {
//                        if (workStatus != null) {
//                            mTextView.append("SimpleWorkRequest: " + workStatus.state.name + "\n")
//                        }
//
//                        if (workStatus != null && workStatus.state.isFinished) {
//                            val message = workStatus.outputData.getString(MyWorker.EXTRA_OUTPUT_MESSAGE)//, "Default message");
//                            mTextView.append("SimpleWorkRequest (Data): " + message!!)
//                        }
//                    }
//                })
    }

    private lateinit var navController: NavController
}