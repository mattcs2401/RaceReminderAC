package mcssoft.com.racereminderac.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_fragment.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.model.RaceObserver
import mcssoft.com.racereminderac.ui.dialog.TimePickDialog
import mcssoft.com.racereminderac.utility.RaceTime
import mcssoft.com.racereminderac.utility.eventbus.TimeMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EditFragment : Fragment(), View.OnClickListener , View.OnTouchListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        initialiseUI(rootView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Get the argumnets (if exist).
        if(arguments != null) {
            editType = arguments?.getInt(getString(R.string.key_edit_type))
            setForEditType(editType!!)
        }
    }

     override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    //</editor-fold>

    /**
     * EventBus returns here (primarily for TimePickDialog).
     * @param dialog - The EventBus message object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(dialog: TimeMessage) {
        if(!dialog.message.isBlank()) {
            btnTime.text = dialog.msg
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Event handler - onClick">
    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_time -> {
                launchTimePickDialog()
            }
            R.id.id_btn_save -> {
                if(checkValues()) {
                    val race: Race //= collateValues()
                    when(editType) {
                        resources.getInteger(R.integer.edit_race_existing) -> {
                            race = collateValues(R.integer.edit_race_existing)
                            raceViewModel.update(race)
                        }
                        resources.getInteger(R.integer.edit_race_new) -> {
                            race = collateValues(R.integer.edit_race_new)
                            raceViewModel.insert(race)
                        }
                    }
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                            .navigate(R.id.id_main_fragment)
                }
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

    /**
     * Update UI elements depending on whether editing an existing Race, or it's a new Race.
     */
    private fun setForEditType(editType: Int) {
        when(editType) {
            resources.getInteger(R.integer.edit_race_existing) -> {
                toolBar.title = getString(R.string.edit_race)
                btnSave.text = getString(R.string.lbl_update)
            }
            resources.getInteger(R.integer.edit_race_new) -> {
                toolBar.title = getString(R.string.new_race)
                btnSave.text = getString(R.string.lbl_save)
                btnTime.text = RaceTime.getInstance().getFormattedDateTime(RaceTime.TIME)
            }
        }
    }

    /**
     * Simple check that all values are entered.
     */
    private fun checkValues(): Boolean {
        // TODO - simple check all values entered.
        return true
    }

    /**
     * Get the UI values into a Race object ready for Update or Insert.
     */
    private fun collateValues(action: Int): Race {
        val race = Race(ccVals.get(npCityCode.value),
                rcVals.get(npRaceCode.value),
                rnVals.get(npRaceNo.value),
                rsVals.get(npRaceSel.value),
                btnTime.text.toString())
//        race.raceDate = RaceTime.getInstance().getFormattedDateTime(RaceTime.DATE)

        when(action) {
            // Update.
            R.integer.edit_race_existing -> {
                race.id = raceId
            }
            // Insert.
            R.integer.edit_race_new -> {
                race.raceDate = RaceTime.getInstance().getFormattedDateTime(RaceTime.DATE)
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

    /**
     * Initialise UI components.
     */
    private fun initialiseUI(view: View) {
        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()

        // Get the toolbar.
        toolBar = activity?.id_toolbar as Toolbar

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

        npRaceNo = id_np_race_num
        rnVals = resources.getStringArray(R.array.raceNum)
        npRaceNo.minValue = 0
        npRaceNo.maxValue = rnVals.size - 1
        npRaceNo.displayedValues = rnVals
        npRaceNo.wrapSelectorWheel = true

        // TODO - if RaceCode == G, then only 8 selections in numberpicker.
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

        // view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)
        raceId = arguments?.getLong(getString(R.string.key_edit_existing))
        val race = raceViewModel.getRace(raceId!!)
        race.observe(viewLifecycleOwner, RaceObserver(race, view))
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private lateinit var toolBar: Toolbar
    private lateinit var npCityCode: NumberPicker
    private lateinit var npRaceCode: NumberPicker
    private lateinit var npRaceNo: NumberPicker
    private lateinit var npRaceSel: NumberPicker
    private var raceId: Long? = null
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