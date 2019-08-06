package mcssoft.com.racereminderac.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import mcssoft.com.racereminderac.ui.dialog.ExtrasDialog
import mcssoft.com.racereminderac.ui.dialog.MultiSelectDialog
import mcssoft.com.racereminderac.ui.dialog.TimePickDialog
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.RacePreferences
import mcssoft.com.racereminderac.utility.RaceTime
import mcssoft.com.racereminderac.utility.eventbus.ExtrasMessage
import mcssoft.com.racereminderac.utility.eventbus.MultiSelMessage
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
        // Set base UI elements.
        initialiseUI(view)

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
     * @param time: The Race time (as Long).
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(time: TimeMessage) {
        raceTimeL = time.time  // keep local copy.
        btnTime.text = RaceTime.getInstance()?.timeFromMillis(raceTimeL)
    }

    /**
     * EventBus return from getting the Race date (from AsyncNoLD).
     * @param raceDT: The Race date/time fields.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(raceDT: RaceMessage) {
        // The Race date.
        this.raceDate = raceDT.theRace.raceDate
        // Additional, get the Race time in mSec (for copy function).
        this.raceTimeL = raceDT.theRace.raceTimeL
    }

    /**
     * EventBus return from the MultiSelect dialog.
     * @param multiSel: An Array<String> of the selected values.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(multiSel: MultiSelMessage) {
        // Note: If the OK or Cancel button was not clicked on the MultiSelectDialog, this
        //       onMessageEvent won't happen.
        if(multiSel.values == null) {
            // The message payload has no data, so just clear the multi select CB.
//            btnMultiSel.isChecked = false
            listMultiSel = arrayOf("","","","")  // clear any previous values.
            setMultiSelViews(false)
        } else {
            // The message payload has 1 or more race selects.
            listMultiSel = multiSel.values!!
            // set number picker to 1st value.
            npRaceSel.value = rsVals.indexOf(listMultiSel[0])
            // set views.
            setMultiSelViews(true)
        }
    }

    /** EventBus return from the ExtrasDialog. **/
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(extras: ExtrasMessage) {
        // Note: If the OK or Cancel button was not clicked on the ExtrasDialog, this onMessageEvent
        //       won't happen.
        listExtras = extras.values     // this will contain something ("defaults").
        setExtrasViews()
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
                    when (editType) {
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
            R.id.id_btn_multi_sel -> {
                if(isMultiSel || allowMultiSel) {
                    setMultiSelVisible(true)
                    launchMultiSelDialog()
                } else {
                    setMultiSelVisible(false)
                }
            }
            R.id.id_btn_additional -> {
                launchExtrasDialog()
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
        val race: Race
        if(isMultiSel || allowMultiSel) {
            // Two or more entries already exist in the backing data, or multi select is set in the
            // Preferences.
            race = collateMultiSelect()
        } else {
            // Get the base values.
            race = collate()
        }

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

    private fun collateMultiSelect() : Race {

        if(listMultiSel[0] == "") {
            // Nothing was selected so use a default.
            listMultiSel[0] = rsVals[npRaceSel.value]
        }
        val race = Race(ccVals[npCityCode.value],
                rcVals[npRaceCode.value],
                rnVals[npRaceNo.value],
                listMultiSel[0],
                btnTime.text.toString())
        race.raceTimeL = raceTimeL
        race.raceSel2 = listMultiSel[1]
        race.raceSel3 = listMultiSel[2]
        race.raceSel4 = listMultiSel[3]
        return race
    }

    private fun collate() : Race {
        val race = Race(ccVals[npCityCode.value],
                rcVals[npRaceCode.value],
                rnVals[npRaceNo.value],
                rsVals[npRaceSel.value],
                btnTime.text.toString())
        race.raceTimeL = raceTimeL
        race.raceTrainer = listExtras[0]
        race.raceJockey = listExtras[1]
        race.raceHorse = listExtras[2]
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
        // Setup number pickers, buttons etc.
        setupDisplayElements()
        // Set the view model and get the id of the Race object.
        setupViewModel(view)
        // Update labels etc depending on edit type, e.g. new, or copy etc.
        setupForEditType()
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
        setupNumberPickers()
        // Set the Time button and listener.
        setupButtons()
        // Set for multi select.
        setupForMultiSelect()
        // Extras (additional).
        setupAdditional()
    }

    /**
     * Setup the selector wheel pickers.
     */
    private fun setupNumberPickers() {
        // Set the 'pickers'.
        npCityCode = id_np_city_code
        ccVals = resources.getStringArray(R.array.cityCodes)
        npCityCode.minValue = 0
        npCityCode.maxValue = ccVals.size - 1
        npCityCode.displayedValues = ccVals
        npCityCode.wrapSelectorWheel = true

        npRaceCode = id_np_race_code
        rcVals = resources.getStringArray(R.array.raceCodes)
        npRaceCode.minValue = 0
        npRaceCode.maxValue = rcVals.size - 1
        npRaceCode.displayedValues = rcVals
        npRaceCode.wrapSelectorWheel = true
        npRaceCode.setOnValueChangedListener(this)    // when race code of 'G'.

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
//        npRaceSel.setOnClickListener(this)
    }

    /**
     * Set the Race Time, Edit and Save buttons, and listeners (default values).
     */
    private fun setupButtons() {
        // Multi select button.
        btnMultiSel = id_btn_multi_sel
        btnMultiSel.setOnClickListener(this)
        // Set the Race Time button and listener.
        btnTime = id_btn_time
        btnTime.setOnClickListener(this)
        // Set the Save button and listener.
        btnSave = id_btn_save
        btnSave.setOnClickListener(this)
        // Set the Additional button.
        btnExtras = id_btn_additional
        btnExtras.setOnClickListener(this)
    }

    /**
     * Set the multi-select checkbox and listener, and selection text fields.
     */
    private fun setupForMultiSelect() {
        // Get current multi select Preference.
        allowMultiSel = RacePreferences.getInstance()!!.getRaceMultiSelect(activity!!)
        // Get any multi select values from the arguments.
        if (arguments!!.getBoolean(getString(R.string.key_edit_existing_multi))) {
            listMultiSel = arguments?.getStringArray(getString(R.string.key_edit_existing_vals)) as Array<String>
            listExtras = listMultiSel.copyOfRange(4,7)
        }
        // Quick and dirty check to see if more than one multi select value exists in the arguments ?
        isMultiSel = listMultiSel[1] != ""

        btnMultiSel = id_btn_multi_sel
        tvMultiSel0 = id_tv_multi_sel0
        tvMultiSel1 = id_tv_multi_sel1
        tvMultiSel2 = id_tv_multi_sel2
        tvMultiSel3 = id_tv_multi_sel3
        tvMultiSelSelects = id_tv_selects_label

        if (isMultiSel || allowMultiSel) {
            setMultiSelVisible(true)
            setMultiSelViews(true)
            btnMultiSel.isEnabled = true
        } else {
            setMultiSelViews(false)
            setMultiSelVisible(false)
            btnMultiSel.isEnabled = false
        }
    }

    private fun setupAdditional() {
        tvTrainer = id_tv_trainer
        tvTrainerName = id_tv_trainer_name
        tvJockey  = id_tv_jockey
        tvJockeyName  = id_tv_jockey_name
        tvHorse  = id_tv_horse
        tvHorseName  = id_tv_horse_name

        if(isMultiSel || allowMultiSel) {
            setExtrasVisible(false)
        } else {
            setExtrasVisible(true)
            setExtrasViews()
        }
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
     * @Note: This must be called after setupViewModel().
     */
    private fun setupForEditType() {
        when(editType) {
            Constants.EDIT_RACE_UPDATE -> {
                toolBar.title = getString(R.string.edit_race)
                btnSave.text = getString(R.string.lbl_update)
                // Save local copy of Race date.
                getRaceDate(raceId!!)
            }
            Constants.EDIT_RACE_NEW -> {
                toolBar.title = getString(R.string.new_race)
                btnSave.text = getString(R.string.lbl_save)
                btnTime.text = RaceTime.getInstance()?.getFormattedDateTime(Constants.TIME)
                npCityCode.value = ccVals.indexOf(RacePreferences.getInstance()?.getCityCode(activity!!))
                npRaceCode.value = rcVals.indexOf(RacePreferences.getInstance()?.getRaceCode(activity!!))
            }
            Constants.EDIT_RACE_COPY -> {
                toolBar.title = getString(R.string.copy_race)
                btnSave.text = getString(R.string.lbl_copy)
                // Save local copy of Race date.
                getRaceDate(raceId!!)
            }
        }
    }

    /**
     * Set the visibility of the multi select text fields.
     * @param visible: True - set visible, else, not visible.
     */
    private fun setMultiSelVisible(visible: Boolean) {
        when(visible) {
            true -> {
                tvMultiSel0.visibility = View.VISIBLE
                tvMultiSel1.visibility = View.VISIBLE
                tvMultiSel2.visibility = View.VISIBLE
                tvMultiSel3.visibility = View.VISIBLE
                tvMultiSelSelects.visibility = View.VISIBLE
            }
            false -> {
                tvMultiSel0.visibility = View.GONE
                tvMultiSel1.visibility = View.GONE
                tvMultiSel2.visibility = View.GONE
                tvMultiSel3.visibility = View.GONE
                tvMultiSelSelects.visibility = View.GONE
            }
        }
    }

    /**
     * Set the values of the multi select text views.
     * @param setViews: True - set respective values as per the backing array listMultiSel.
     *                  False - set as empty string/text.
     * Note: Called in - onMessageEvent(multiSel: MultiSelMessage)
     */
    private fun setMultiSelViews(setViews: Boolean) {
        when(setViews) {
            true -> {
                tvMultiSel0.text = listMultiSel[0]
                tvMultiSel1.text = listMultiSel[1]
                tvMultiSel2.text = listMultiSel[2]
                tvMultiSel3.text = listMultiSel[3]
            }
            false -> {
                tvMultiSel0.text = ""
                tvMultiSel1.text = ""
                tvMultiSel2.text = ""
                tvMultiSel3.text = ""
            }
        }
    }

    private fun setExtrasVisible(visible: Boolean) {
        if(!visible) {
            btnExtras.isEnabled = false
            tvTrainer.visibility = View.GONE
            tvJockey.visibility = View.GONE
            tvHorse.visibility = View.GONE
            tvTrainerName.visibility = View.GONE
            tvJockeyName.visibility = View.GONE
            tvHorseName.visibility = View.GONE
        } else {
            btnExtras.isEnabled = true
            tvTrainer.visibility = View.VISIBLE
            tvJockey.visibility = View.VISIBLE
            tvHorse.visibility = View.VISIBLE
            tvTrainerName.visibility = View.VISIBLE
            tvJockeyName.visibility = View.VISIBLE
            tvHorseName.visibility = View.VISIBLE
        }
    }

    private fun setExtrasViews() {
        tvTrainerName.text = listExtras[0]
        tvJockeyName.text = listExtras[1]
        tvHorseName.text = listExtras[2]
    }

    private fun launchMultiSelDialog() {
        val dialog = MultiSelectDialog()
        dialog.arguments = setMultiSelDialogArgs()
        dialog.show(activity!!.supportFragmentManager, "multi_select_dialog")
    }

    private fun launchExtrasDialog() {
        val dialog = ExtrasDialog()
        dialog.arguments = setExtrasDialogArgs()
        dialog.show(activity!!.supportFragmentManager, "extras_dialog")
    }

    private fun setMultiSelDialogArgs() : Bundle {
        val bundle = Bundle()
        if(listMultiSel[0] == "") {
            listMultiSel[0] = rsVals[npRaceSel.value]
        }
        bundle.putStringArray(getString(R.string.key_multi_select_dialog_vals), listMultiSel)
        return bundle
    }

    private fun setExtrasDialogArgs() : Bundle {
        val bundle = Bundle()
        listExtras[0] = tvTrainerName.text.toString()
        listExtras[1] = tvJockeyName.text.toString()
        listExtras[2] = tvHorseName.text.toString()
        bundle.putStringArray(getString(R.string.key_extras_dialog_vals), listExtras)
        return bundle
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private lateinit var toolBar: Toolbar             // fragment's toolbar.
    private lateinit var npCityCode: NumberPicker     // city codes.
    private lateinit var npRaceCode: NumberPicker     // race codes.
    private lateinit var npRaceNo: NumberPicker       // race number.
    private lateinit var npRaceSel: NumberPicker      // race selection

    private var raceId: Long? = null                  // race id (set in setupViewModel())
    private var raceTimeL: Long = 0                   // race time as Long.
    private lateinit var raceDate: String             // race date.
    private var editType: Int? = null                 // edit type, e.g. new, update etc.

    private lateinit var btnSave: Button              // save values and exit.
    private lateinit var btnTime: Button              // set time value (launches timePickDialog).
    private lateinit var timePickDialog: DialogFragment

    private lateinit var raceViewModel: RaceViewModel // race view model (set in setupViewModel())

    private lateinit var ccVals: Array<String>        // city codes values.
    private lateinit var rcVals: Array<String>        // race codes values.
    private lateinit var rnVals: Array<String>        // race number values.
    private lateinit var rsVals: Array<String>        // race selection values.

    private var isMultiSel: Boolean = false           // multi select values exist (previous edit).
    private var allowMultiSel: Boolean = false        // multi select set in Preferences.

    private lateinit var btnMultiSel: Button          // multi select button.
    private lateinit var tvMultiSel0: TextView        // multi select - selection one (default).
    private lateinit var tvMultiSel1: TextView        // multi select - selection two.
    private lateinit var tvMultiSel2: TextView        // multi select - selection three.
    private lateinit var tvMultiSel3: TextView        // multi select - selection four.
    private lateinit var tvMultiSelSelects: TextView  // multi select selections label.

    private var listMultiSel: Array<String> = arrayOf("","","","")   // multi select backing data.

    private lateinit var btnExtras: Button
    private var listExtras: Array<String> = arrayOf("","","")
    private lateinit var tvTrainer: TextView
    private lateinit var tvTrainerName: TextView
    private lateinit var tvJockey: TextView
    private lateinit var tvJockeyName: TextView
    private lateinit var tvHorse: TextView
    private lateinit var tvHorseName: TextView
    //</editor-fold>
}