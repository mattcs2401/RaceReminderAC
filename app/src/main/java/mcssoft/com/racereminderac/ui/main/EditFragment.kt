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
import androidx.lifecycle.Observer
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

        initialise()

        // Get the argumnets (if exist).
        if(arguments != null) {
            editType = arguments?.getString(getString(R.string.key_edit_type))
            when(editType) {
                "edit_type_existing" -> {
                    toolBar.title = "Edit Race"
                    btnSave.text = "Update"
                    val id = arguments?.getLong(getString(R.string.key_edit_existing))
                    populateFromArgs(id!!)
                }
                "edit_type_new" -> {
                    toolBar.title = "New Race"
                    btnSave.text = "Save"
                }
            }
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
                        }
                        "edit_type_new" -> {
                            raceViewModel.insert(race)
                        }
                    }
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                              .navigate(R.id.id_main_fragment)
//                    Snackbar.make(rootView, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show()
                }
            }
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            when (view.id) {
//                R.id.etCityCode -> (activity as IShowCodes)
//                        .onShowCodes(R.integer.city_codes_dialog_id, view)
//                R.id.etRaceCode -> (activity as IShowCodes)
//                        .onShowCodes(R.integer.race_codes_dialog_id, view)
//                R.id.etRaceTime -> showTimePicker()
            }
            return true
        }
        return false
    }

    private fun checkValues(): Boolean {
        // TBA - basic check on values entered.
        return true
    }

    private fun initialise() {
        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()
        // Set the Save button and listener.
        btnSave = rootView.findViewById<Button>(R.id.id_btn_save)
        btnSave.setOnClickListener(this)
        // Get the toolbar.
        toolBar = activity?.findViewById(R.id.id_toolbar) as Toolbar
        // Get the Race related views.
        etCityCode = rootView.findViewById(R.id.etCityCode)
        etRaceCode = rootView.findViewById(R.id.etRaceCode)
        etRaceNum = rootView.findViewById(R.id.etRaceNum)
        etRaceSel = rootView.findViewById(R.id.etRaceSel)
        etRaceTime = rootView.findViewById(R.id.etRaceTime)

        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)
        val id = arguments?.getLong(getString(R.string.key_edit_existing))
        raceViewModel.getRace(id!!).observe(activity!!, RaceObserver(raceViewModel.getRace(id), rootView))

    }

    private fun collateValues(): Race {
        return Race(
                etCityCode.text.toString(),
                etRaceCode.text.toString(),
                etRaceNum.text.toString(),
                etRaceSel.text.toString(),
                etRaceTime.text.toString())
    }

    private fun populateFromArgs(id: Long) {
        // this works but can't be used to update UI.
//        raceViewModel.getRace(id).observe(activity!!, RaceObserver(raceViewModel.getRace(id), rootView))

        // TBA - for now.
//        raceViewModel.getRa
// ce(id).observe(activity!!, Observer<Race> { race ->
//            etCityCode.setText(race?.cityCode)
//            etRaceCode.setText(race?.raceCode)
//            etRaceNum.setText(race?.raceNum)
//            etRaceSel.setText(race?.raceSel)
//            etRaceTime.setText(race?.raceTime)
//        })
    }

    private lateinit var rootView: View
    private lateinit var toolBar: Toolbar

    private lateinit var etCityCode : EditText
    private lateinit var etRaceCode : EditText
    private lateinit var etRaceNum : EditText
    private lateinit var etRaceSel : EditText
    private lateinit var etRaceTime : EditText
    private lateinit var btnSave: Button

    private lateinit var raceViewModel: RaceViewModel

    private var editType: String? = null

}
