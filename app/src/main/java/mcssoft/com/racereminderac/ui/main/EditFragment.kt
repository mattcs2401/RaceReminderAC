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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.edit_fragment.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
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
            var editType = arguments?.getString(getString(R.string.key_edit_type))
            when(editType) {
                "edit_type_existing" -> {
                    (activity?.findViewById(R.id.id_toolbar) as Toolbar).title = "Edit Race"
                    var race = arguments?.getParcelable<Race>(getString(R.string.key_edit_existing))
                    populateFromArgs(race)
                }
                "edit_type_new" -> {
                    (activity?.findViewById(R.id.id_toolbar) as Toolbar).title = "New Race"

                }
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_save -> {
                if(checkValues()) {
                    Snackbar.make(rootView, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
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

        (rootView.findViewById<Button>(R.id.id_btn_save)).setOnClickListener(this)

        etCityCode = rootView.findViewById(R.id.etCityCode)
        etRaceCode = rootView.findViewById(R.id.etRaceCode)
        etRaceNum = rootView.findViewById(R.id.etRaceNum)
        etRaceSel = rootView.findViewById(R.id.etRaceSel)
        etRaceTime = rootView.findViewById(R.id.etRaceTime)

        viewModel = ViewModelProviders.of(this).get(RaceViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun populateFromArgs(race: Race?) {
        etCityCode.setText(race?.cityCode)
        etRaceCode.setText(race?.raceCode)
        etRaceNum.setText(race?.raceNum)
        etRaceSel.setText(race?.raceSel)
        etRaceTime.setText(race?.raceTime)
    }

    private lateinit var rootView: View
    private lateinit var etCityCode : EditText
    private lateinit var etRaceCode : EditText
    private lateinit var etRaceNum : EditText
    private lateinit var etRaceSel : EditText
    private lateinit var etRaceTime : EditText
    private lateinit var btnSave: Button
    private lateinit var viewModel: RaceViewModel

}
