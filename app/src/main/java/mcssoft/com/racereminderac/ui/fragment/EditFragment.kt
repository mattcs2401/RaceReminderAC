package mcssoft.com.racereminderac.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.edit_fragment.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.observer.RaceObserver
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.ui.dialog.TimePickDialog
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.RaceTime
import mcssoft.com.racereminderac.utility.eventbus.RaceMessage
import mcssoft.com.racereminderac.utility.eventbus.TimeMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EditFragment : Fragment(), View.OnClickListener , View.OnTouchListener, NumberPicker.OnValueChangeListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view

        // set base UI elements.
        initialiseUI(rootView)

        Log.d("tag","EditFragment.onViewCreated")
    }

     override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

         Log.d("tag","EditFragment.onStart")
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)

        Log.d("tag","EditFragment.onStop")
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: EventBus">
    /**
     * EventBus return for TimePickDialog.
     * @param time: The EventBus message object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(time: TimeMessage) {
        raceTimeL = time.time  // keep local copy.
        btnTime.text = RaceTime.getInstance()?.timeFromMillis(raceTimeL)
    }

    /**
     * EventBus return from getting the Race date (from AsyncNoLD).
     * @param race: The Race object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(race: RaceMessage) {
        // The Race date.
        this.raceDate = race.theRace.raceDate
        // Additional, get the Race time in mSec (for copy function).
        this.raceTimeL = race.theRace.raceTimeL
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Event handler - onClick">
    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_time -> {
                launchTimePickDialog()
            }
            R.id.id_btn_save -> {
                val race: Race
                when(editType) {
                    Constants.EDIT_RACE_UPDATE -> {
                        race = collateValues(Constants.EDIT_RACE_UPDATE)
                        raceViewModel.update(race)
                    }
                    Constants.EDIT_RACE_NEW -> {
                        race = collateValues(Constants.EDIT_RACE_NEW)
                        raceViewModel.insert(race)
                    }
                    Constants.EDIT_RACE_COPY -> {
                        race = collateValues(Constants.EDIT_RACE_COPY)
                        raceViewModel.insert(race)
                    }
                }
                Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                            .navigate(R.id.id_main_fragment)
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Event handler - onTouch">
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            when(view.id) {
                // TBA
            }
            return true
        }
        return false
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Event handler - onValueChange">
    override fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int) {
        if(picker.id == R.id.id_np_race_code) {
            // greyhound race only has 8 runners.
            if (picker.displayedValues[newVal] == "G") {
                npRaceSel.maxValue = rsVals.size - 17
            } else {
                npRaceSel.maxValue = rsVals.size - 1
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    /**
     * Get the UI values into a Race object ready for Update or Insert.
     */
    private fun collateValues(action: Int): Race {
        // Get the base values.
        val race = Race(ccVals[npCityCode.value],
                rcVals[npRaceCode.value],
                rnVals[npRaceNo.value],
                rsVals[npRaceSel.value],
                btnTime.text.toString())
            race.raceTimeL = raceTimeL

        when(action) {
            // Update.
            Constants.EDIT_RACE_UPDATE -> {
                race.id = raceId
                race.raceDate = raceDate
            }
            // Insert as New.
            Constants.EDIT_RACE_NEW -> {
                race.raceDate = RaceTime.getInstance()?.getFormattedDateTime(Constants.DATE)!!
            }
            // Insert as Copy.
            Constants.EDIT_RACE_COPY -> {
                race.raceDate = raceDate
            }
        }
        return race
    }

    /**
     * Show the TimePicker dialog.
     */
    private fun launchTimePickDialog() {
        val fragTrans: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragTrans.addToBackStack(null)
        timePickDialog = TimePickDialog()
        val args = Bundle()
        val time = btnTime.text.toString()
        if(!time.isBlank()) {
            args.putString("key", time)
        }
        timePickDialog.arguments = args
        timePickDialog.show(fragTrans, getString(R.string.tp_tag))
    }

    private fun getRaceDate(id: Long) {
        // Note: "returns" via EventBus.
        raceViewModel.getRaceNoLD(id)
    }

    /**
     * Initialise UI components.
     */
    private fun initialiseUI(view: View) {
        // setup numberpickers, buttons etc.
        setupDisplayElements()
        // set the view model and get the id of the Race object.
        setupViewModel(view)
        // update labels etc depending on edit type, e.g. new, or copy etc.
        setupForEditType()//editType!!)
    }

    /**
     * Setup base UI elements.
     */
    private fun setupDisplayElements() {
        // Hide the bottom nav view.
        (activity?.findViewById(R.id.id_bottom_nav_view) as BottomNavigationView).visibility = View.GONE

        // Get the toolbar.
        toolBar = activity?.id_toolbar as Toolbar

        // Set the 'pickers'.
        npCityCode = id_np_city_code
        ccVals = resources.getStringArray(R.array.cityCodes)
        npCityCode.minValue = 0
        npCityCode.maxValue = ccVals.size - 1
        npCityCode.displayedValues = ccVals
        npCityCode.wrapSelectorWheel = true
        npCityCode.value = ccVals.indexOf(RacePreferences.getInstance()?.getCityCode(activity!!))

        npRaceCode = id_np_race_code
        rcVals = resources.getStringArray(R.array.raceCodes)
        npRaceCode.minValue = 0
        npRaceCode.maxValue = rcVals.size - 1
        npRaceCode.displayedValues = rcVals
        npRaceCode.wrapSelectorWheel = true
        npRaceCode.setOnValueChangedListener(this)
        npRaceCode.value = rcVals.indexOf(RacePreferences.getInstance()?.getRaceCode(activity!!))

        npRaceNo = id_np_race_num
        rnVals = resources.getStringArray(R.array.raceNum)
        npRaceNo.minValue = 0
        npRaceNo.maxValue = rnVals.size - 1
        npRaceNo.displayedValues = rnVals
        npRaceNo.wrapSelectorWheel = true

        npRaceSel = id_np_race_sel
        rsVals = resources.getStringArray(R.array.raceSel)
        npRaceSel.minValue = 0
        npRaceSel.maxValue = rsVals.size - 1
        npRaceSel.displayedValues = rsVals
        npRaceSel.wrapSelectorWheel = true

        // Set the Time button and listener.
        btnTime = id_btn_time
        btnTime.setOnClickListener(this)

        // Set the Save button and listener.
        btnSave = id_btn_save
        btnSave.setOnClickListener(this)
    }

    /**
     * Get the Race object id and setup ViewModel details.
     */
    private fun setupViewModel(view: View) {
        // Get the edit type (new, copy or update).
        editType = arguments?.getInt(getString(R.string.key_edit_type))

        // Get Race id if update or copy.
        when(editType) {
            Constants.EDIT_RACE_UPDATE -> {
                raceId = arguments?.getLong(getString(R.string.key_edit_existing))
            }
            Constants.EDIT_RACE_COPY -> {
                raceId = arguments?.getLong(getString(R.string.key_edit_copy))
            }
        }

        // Set the view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)

        // If not a new Race object (it's id wouldn't exist yet), observe changes.
        if(editType != Constants.EDIT_RACE_NEW) {
            val race = raceViewModel.getRace(raceId!!)
            race.observe(viewLifecycleOwner, RaceObserver(race, view))
        }
    }

    /**
     * Update UI elements depending on whether editing an existing Race, entering a new Race, or
     * copying a previous Race.
     */
    private fun setupForEditType() {
        when(editType) {
            Constants.EDIT_RACE_UPDATE -> {
                toolBar.title = getString(R.string.edit_race)
                btnSave.text = getString(R.string.lbl_update)
                // save local copy of Race date.
                getRaceDate(raceId!!)
            }
            Constants.EDIT_RACE_NEW -> {
                toolBar.title = getString(R.string.new_race)
                btnSave.text = getString(R.string.lbl_save)
                btnTime.text = RaceTime.getInstance()?.getFormattedDateTime(Constants.TIME)
            }
            Constants.EDIT_RACE_COPY -> {
                toolBar.title = getString(R.string.copy_race)
                btnSave.text = getString(R.string.lbl_copy)
                // save local copy of Race date.
                getRaceDate(raceId!!)
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private lateinit var toolBar: Toolbar
    private lateinit var npCityCode: NumberPicker
    private lateinit var npRaceCode: NumberPicker
    private lateinit var npRaceNo: NumberPicker
    private lateinit var npRaceSel: NumberPicker
    private var raceId: Long? = null
    private var raceTimeL: Long = 0
    private lateinit var raceDate: String
    private var editType: Int? = null
    private lateinit var btnSave: Button
    private lateinit var btnTime: Button
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var rootView: View
    private lateinit var ccVals: Array<String>
    private lateinit var rcVals: Array<String>
    private lateinit var rnVals: Array<String>
    private lateinit var rsVals: Array<String>
    private lateinit var timePickDialog: DialogFragment
    //</editor-fold>
}