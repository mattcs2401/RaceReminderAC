package mcssoft.com.racereminderac.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.model.RaceObserver
import mcssoft.com.racereminderac.model.RaceViewModel

class EditFragment : Fragment(), View.OnClickListener, View.OnTouchListener {

    companion object {
        //fun newInstance() = EditFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.edit_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialiseUI()

        // Get the argumnets (if exist).
        if(arguments != null) {
            editType = arguments?.getString(getString(R.string.key_edit_type))
            setForEditType(editType!!)
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_save -> {
                if(checkValues()) {
                    var race = collateValues()
                    when(editType) {
                        "edit_type_existing" -> {
                            raceViewModel.update(race)
//                            raceViewModel.update(raceCache)
                        }
                        "edit_type_new" -> {
                            raceViewModel.insert(race)
//                            raceViewModel.insert(raceCache)
                        }
                    }
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                              .navigate(R.id.id_main_fragment)
                }
            }
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        // set the argumnents
        var args = Bundle()
        args.putInt("key_view_id", view.id)
        // set the fragment transaction
        val fragTrans: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        // set the dialog.
        val kbdDialog: DialogFragment = KeyboardFragment()
        kbdDialog.arguments = args
        // show
        fragTrans.addToBackStack(null)
        kbdDialog.show(fragTrans, "keyboard_dialog")

        return true
    }

    /**
     * Simple check that all values are entered.
     */
    private fun checkValues(): Boolean {
        // TBA
        return true
    }

    /**
     * Setup UI and view model.
     */
    private fun initialiseUI() {
        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()
        // keyboard.
//        raceKbd = RaceKeyboard(activity!!, null, R.id.id_kbdView, null)
        // Set the Save button and listener.
        btnSave = rootView.findViewById<Button>(R.id.id_btn_save)
        btnSave.setOnClickListener(this)
        // Get the toolbar.
        toolBar = activity?.findViewById(R.id.id_toolbar) as Toolbar
        // Get the Race related views.
        etCityCode = rootView.findViewById(R.id.etCityCode)
        etCityCode.setOnTouchListener(this)
//        raceKbd.register(rootView, R.id.etCityCode)

        etRaceCode = rootView.findViewById(R.id.etRaceCode)
        etRaceCode.setOnTouchListener(this)
//        raceKbd.register(rootView, R.id.etRaceCode)

        etRaceNum = rootView.findViewById(R.id.etRaceNum)
        etRaceNum.setOnTouchListener(this)
//        raceKbd.register(rootView,R.id.etRaceNum)

        etRaceSel = rootView.findViewById(R.id.etRaceSel)
        etRaceSel.setOnTouchListener(this)
//        raceKbd.register(rootView,R.id.etRaceSel)

        etRaceTime = rootView.findViewById(R.id.etRaceTime)
        etRaceTime.setOnTouchListener(this)
//        raceKbd.register(rootView,R.id.etRaceTime)
        // view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)
        raceId = arguments?.getLong(getString(R.string.key_edit_existing))
        raceViewModel.getRaceLD(raceId!!).observe(activity!!, RaceObserver(raceViewModel.getRaceLD(raceId!!), rootView))
    }

    /**
     * get the UI values into a Race object ready for Update or Insert.
     */
    private fun collateValues(): Race {
        val race = Race(
                etCityCode.text.toString(),
                etRaceCode.text.toString(),
                etRaceNum.text.toString(),
                etRaceSel.text.toString(),
                etRaceTime.text.toString())
        race.id = raceId
        return race
    }

    /**
     * Update UI elements depending on whether editing an existing Race, or it's a new Race.
     */
    private fun setForEditType(editType: String) {
        when(editType) {
            "edit_type_existing" -> {
                toolBar.title = "Edit Race"
                btnSave.text = "Update"
            }
            "edit_type_new" -> {
                toolBar.title = "New Race"
                btnSave.text = "Save"
            }
        }
    }

    private lateinit var rootView: View
    private lateinit var toolBar: Toolbar

    private var raceId: Long? = null
    private lateinit var etCityCode : EditText
    private lateinit var etRaceCode : EditText
    private lateinit var etRaceNum : EditText
    private lateinit var etRaceSel : EditText
    private lateinit var etRaceTime : EditText
    private lateinit var btnSave: Button

    private lateinit var raceViewModel: RaceViewModel

    private var editType: String? = null

    private lateinit var raceCache: Race

//    private lateinit var raceKbd: RaceKeyboard

}
