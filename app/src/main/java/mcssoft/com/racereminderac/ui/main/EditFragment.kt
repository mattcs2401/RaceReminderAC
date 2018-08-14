package mcssoft.com.racereminderac.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.model.RaceViewModel

class EditFragment : Fragment(), View.OnClickListener {

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
                // TBA
                Snackbar.make(rootView, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            }
        }
    }

    private fun initialise() {
        // TODO - this will depend on what the fragment is used for, e.g. New, Edit etc
        (activity?.findViewById(R.id.id_toolbar) as Toolbar).title = "New Race"

        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()

        (rootView.findViewById<Button>(R.id.id_btn_save)).setOnClickListener(this)

        viewModel = ViewModelProviders.of(this).get(RaceViewModel::class.java)
        // TODO: Use the ViewModel

    }

    private fun populateFromArgs(race: Race?) {
        (rootView.findViewById<EditText>(R.id.etCityCode)).setText(race?.cityCode)
        (rootView.findViewById<EditText>(R.id.etRaceCode)).setText(race?.raceCode)
        (rootView.findViewById<EditText>(R.id.etRaceNum)).setText(race?.raceNum)
        (rootView.findViewById<EditText>(R.id.etRaceSel)).setText(race?.raceSel)
        (rootView.findViewById<EditText>(R.id.etRaceTime)).setText(race?.raceTime)
    }

    private lateinit var rootView: View
    private lateinit var btnSave: Button
    private lateinit var viewModel: RaceViewModel

}
