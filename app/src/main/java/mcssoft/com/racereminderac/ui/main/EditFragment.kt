package mcssoft.com.racereminderac.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
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
            editType = arguments!!.getString(getString(R.string.key_edit_type))
            if(editType != null && arguments!!.size() == 2) {
                setForEditType(editType!!)
            } else {
                dialogVal = arguments!!.getString("letter_key")
                dialogType = arguments!!.getString("dialog_key")
                setForDialogType(dialogVal!!, dialogType!!)
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_save -> {
                if(checkValues()) {
//                    var race = collateValues()
                    when(editType) {
                        "edit_type_existing" -> {
//                            raceViewModel.update(race)
                            raceViewModel.update(raceCache)
                        }
                        "edit_type_new" -> {
//                            raceViewModel.insert(race)
                            raceViewModel.insert(raceCache)
                        }
                    }
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                              .navigate(R.id.id_main_fragment)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            when (view.id) {
                R.id.etCityCode -> {
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                            .navigate(R.id.id_city_codes, arguments)
                }
                R.id.etRaceCode -> {
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                            .navigate(R.id.id_race_codes)
                }
            }
            return true
        }
        return false
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
        etCityCode.setRawInputType(InputType.TYPE_NULL)
        etCityCode.setOnTouchListener(this)
        etRaceCode = rootView.findViewById(R.id.etRaceCode)
        etRaceCode.setRawInputType(InputType.TYPE_NULL)
        etRaceCode.setOnTouchListener(this)
        etRaceNum = rootView.findViewById(R.id.etRaceNum)
        etRaceSel = rootView.findViewById(R.id.etRaceSel)
        etRaceTime = rootView.findViewById(R.id.etRaceTime)

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

    /**
     * Update UI and cache for CityCodes or RaceCodes UI elements.
     */
    private fun setForDialogType(dialogVal: String, dialogType: String) {
        // TODO update cache.
        var race = raceViewModel.getRaceLD(raceId!!).getValue()
        when(dialogType) {
            "city_codes" -> {
                etCityCode.setText(dialogVal)
                race?.cityCode = dialogVal
            }
            "race_codes" -> {
                etRaceCode.setText(dialogVal)
                race?.raceCode = dialogVal
            }
        }
    }

    private fun setCache() {
        raceCache = collateValues()
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
    private var dialogVal: String? = null
    private var dialogType: String? = null

    private lateinit var raceCache: Race

}
