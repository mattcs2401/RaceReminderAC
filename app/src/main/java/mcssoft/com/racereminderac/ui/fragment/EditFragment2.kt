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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_fragment2.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.model.RaceViewModel
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.model.RaceObserver
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EditFragment2 : Fragment(), View.OnClickListener , View.OnTouchListener {

    //<editor-fold defaultstate="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_fragment2, container, false)
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
     * @param event - The EventBus message object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(event: EventMessage) {
        if(!event.message.isBlank()) {
//            doOnMessageEvent(event)
        } else {
            // Nothing was selected in the dialog except for the OK button.
//            doSnackbar(event.ident, event.ctx)
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Event handler - onClick">
    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_save -> {
                if(checkValues()) {
                    var race = collateValues()
                    when(editType) {
                        R.integer.edit_race_existing -> {
                            raceViewModel.update(race)
                        }
                        R.integer.edit_race_new -> {
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
            // set the fragment transaction
            val fragTrans: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            fragTrans.addToBackStack(null)
            // set the dialog and show.
            when(view.id) {
//                R.id.id_etCityCode -> launchCityCodes()
//                R.id.id_etRaceCode -> launchRaceCodes()
//                R.id.id_etRaceNum -> launchRaceNum()
//                R.id.id_etRaceSel -> launchRaceSel()
//                R.id.id_etRaceTime -> launchTimePick()
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
                btnSave.text = getString(R.string.update)
            }
            resources.getInteger(R.integer.edit_race_new) -> {
                toolBar.title = getString(R.string.new_race)
                btnSave.text = getString(R.string.save)
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
     * get the UI values into a Race object ready for Update or Insert.
     */
    private fun collateValues(): Race {
        // TODO - get NumberPicker values.
        val race = Race(ccVals.get(npCityCode.value),
                rcVals.get(npRaceCode.value),
                rnVals.get(npRaceNo.value),
                rsVals.get(npRaceSel.value),
                "")
        race.id = raceId
        return race
    }

    private fun initialiseUI(view: View) {
        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()

        // Set the Save button and listener.
        btnSave = id_btn_save
        btnSave.setOnClickListener(this)

        // Get the toolbar.
        toolBar = activity?.id_toolbar as Toolbar

        // Set the number pickers.
        npCityCode = id_np_city_code
        ccVals = resources.getStringArray(R.array.cityCodes)
        npCityCode.minValue = 0
        npCityCode.maxValue = ccVals.size - 1
        npCityCode.displayedValues = ccVals
        npCityCode.wrapSelectorWheel = true
//        npCityCode.setFormatter(this)

        npCityCode.setFormatter(NumberPicker.Formatter { value ->
            ccVals[value]
        })
        npRaceCode = id_np_race_code
        val rcVals = resources.getStringArray(R.array.raceCodes)
        npRaceCode.minValue = 0
        npRaceCode.maxValue = rcVals.size - 1
        npRaceCode.displayedValues = rcVals
        npRaceCode.wrapSelectorWheel = true

        npRaceNo = id_np_race_num
        val rnVals = resources.getStringArray(R.array.raceNum)
        npRaceNo.minValue = 0
        npRaceNo.maxValue = rnVals.size - 1
        npRaceNo.displayedValues = rnVals
        npRaceNo.wrapSelectorWheel = true

        npRaceSel = id_np_race_sel
        val rsVals = resources.getStringArray(R.array.raceSel)
        npRaceSel.minValue = 0
        npRaceSel.maxValue = rsVals.size - 1
        npRaceSel.displayedValues = rsVals
        npRaceSel.wrapSelectorWheel = true

        // view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)
        raceId = arguments?.getLong(getString(R.string.key_edit_existing))
        raceViewModel.getRace(raceId!!).observe(activity!!, RaceObserver(raceViewModel.getRace(raceId!!), view))
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
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var rootView: View
    private lateinit var ccVals: Array<String>
    private lateinit var rcVals: Array<String>
    private lateinit var rnVals: Array<String>
    private lateinit var rsVals: Array<String>
    //</editor-fold>
}