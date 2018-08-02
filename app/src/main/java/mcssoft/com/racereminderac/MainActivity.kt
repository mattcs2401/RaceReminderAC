package mcssoft.com.racereminderac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mcssoft.com.racereminderac.ui.main.EditFragment
import mcssoft.com.racereminderac.ui.main.MainFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
//            mainFragment = MainFragment.newInstance()
//            addFragment(R.id.container, mainFragment as MainFragment)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
        }

        fab = findViewById<FloatingActionButton>(R.id.id_fab)
        fab?.setOnClickListener(this);
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.id_fab -> {
//                editFragment = EditFragment.newInstance()
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EditFragment.newInstance())
//                        .addToBackStack(null)
                        .commit()
                fab?.hide()
//                replaceFragment(R.id.container, editFragment as EditFragment)
            }
            else -> {}
        }
    }

    // Kotlin trickery.
    // https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
    private fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }


    fun AppCompatActivity.replaceFragment(frameId: Int, newFragment: Fragment) {
        supportFragmentManager.inTransaction{replace(frameId, newFragment)}
    }

    private var fab: FloatingActionButton? = null
    private var mainFragment: Fragment? = null
    private var editFragment: Fragment? = null
}