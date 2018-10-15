package mcssoft.com.racereminderac.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_fragment.*
import kotlinx.android.synthetic.main.edit_fragment2.*
import kotlinx.android.synthetic.main.toolbar_base.*
import mcssoft.com.racereminderac.entity.Race
import mcssoft.com.racereminderac.model.RaceObserver
import mcssoft.com.racereminderac.model.RaceViewModel
import org.greenrobot.eventbus.EventBus
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.EventMessage
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

//    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//    }

//    override fun onStart() {
//        super.onStart()
//        EventBus.getDefault().register(this)
//    }

//    override fun onStop() {
//        super.onStop()
//        EventBus.getDefault().unregister(this)
//    }
    //</editor-fold>

//    /**
//     * EventBus returns here.
//     * @param event - The EventBus message object.
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
//    fun onMessageEvent(event: EventMessage) {
//        if(!event.message.isBlank()) {
////            doOnMessageEvent(event)
//        } else {
//            // Nothing was selected in the dialog except for the OK button.
////            doSnackbar(event.ident, event.ctx)
//        }
//    }

    //<editor-fold defaultstate="collapsed" desc="Region: Event handler - onClick">
    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_save -> {
                if(checkValues()) {
//                    var race = collateValues()
//                    when(editType) {
//                        R.integer.edit_race_existing -> {
//                            raceViewModel.update(race)
//                        }
//                        R.integer.edit_race_new -> {
//                            raceViewModel.insert(race)
//                        }
//                    }
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
//                btnSave.text = getString(R.string.update)
            }
            resources.getInteger(R.integer.edit_race_new) -> {
                toolBar.title = getString(R.string.new_race)
//                btnSave.text = getString(R.string.save)
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
        val race = Race("","","","",""
//                etCityCode.text.toString(),
//                etRaceCode.text.toString(),
//                etRaceNum.text.toString(),
//                etRaceSel.text.toString(),
//                etRaceTime.text.toString()
        )
        race.id = raceId
        return race
    }

    private fun initialiseUI(view: View) {
        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()

        // Set the Save button and listener.
//        btnSave = id_btn_save
//        btnSave.setOnClickListener(this)

        // Get the toolbar.
        toolBar = activity?.id_toolbar as Toolbar

        npCityCode = id_np_city_code
        

//        // Get the Race related views.
//        etCityCode = id_etCityCode
//        etCityCode.setOnTouchListener(this)
//        etCityCode.showSoftInputOnFocus = false  // use dialog.
//
//        etRaceCode = id_etRaceCode
//        etRaceCode.setOnTouchListener(this)
//        etRaceCode.showSoftInputOnFocus = false  // use dialog.
//
//        etRaceNum = id_etRaceNum
//        etRaceNum.setOnTouchListener(this)
//        etRaceNum.showSoftInputOnFocus = false  // use dialog.
//
//        etRaceSel = id_etRaceSel
//        etRaceSel.setOnTouchListener(this)
//        etRaceSel.showSoftInputOnFocus = false  // use dialog.
//
//        etRaceTime = id_etRaceTime
//        etRaceTime.setOnTouchListener(this)
//        etRaceTime.showSoftInputOnFocus = false

        // view model.
//        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)
//        raceId = arguments?.getLong(getString(R.string.key_edit_existing))
//        raceViewModel.getRace(raceId!!).observe(activity!!, RaceObserver(raceViewModel.getRace(raceId!!), view))

    }


    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private lateinit var toolBar: Toolbar
    private lateinit var npCityCode: NumberPicker

    private var raceId: Long? = null
//    private lateinit var etCityCode : EditText
//    private lateinit var etRaceCode : EditText
//    private lateinit var etRaceNum : EditText
//    private lateinit var etRaceSel : EditText
//    private lateinit var etRaceTime : EditText
    private lateinit var btnSave: Button

    private lateinit var raceViewModel: RaceViewModel

    private var editType: Int? = null

//    private lateinit var cityCodesDialog: DialogFragment
//    private lateinit var raceCodesDialog: DialogFragment
//    private lateinit var numberPadDialog: DialogFragment
//    private lateinit var timePickDialog: DialogFragment

    private lateinit var rootView: View
    //</editor-fold>
}