package mcssoft.com.racereminderac.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import mcssoft.com.racereminderac.ui.dialog.CityCodesDialog
import mcssoft.com.racereminderac.ui.dialog.NumberPadDialog
import mcssoft.com.racereminderac.ui.dialog.RaceCodesDialog
import org.greenrobot.eventbus.EventBus
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe

class EditFragment : Fragment(), View.OnClickListener , View.OnTouchListener {

//    companion object { }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseUI(view)
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    /**
     * EventBus returns here.
     * @param event - The EventBus message object.
     */
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEvent(event: EventMessage) {
        val msg = event.message
        when(event.ident) {
            R.integer.race_codes_dialog_id -> {
                etRaceCode.setText(msg)
            }
            R.integer.city_codes_dialog_id -> {
                etCityCode.setText(msg)
            }
            R.integer.number_pad_dialog_id -> {
                when(event.contex) {
                    R.integer.npCtxRaceNum -> {
                        etRaceNum.setText(msg)
                    }
                    R.integer.npCtxRaceSel -> {
                        etRaceSel.setText(msg)
                    }
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
                        resources.getInteger(R.integer.edit_race_existing) -> {
                            raceViewModel.update(race)
                        }
                        resources.getInteger(R.integer.edit_race_new) -> {
                            raceViewModel.insert(race)
                        }
                    }
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                              .navigate(R.id.id_main_fragment)
                }
            }
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            // set the fragment transaction
            val fragTrans: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            fragTrans.addToBackStack(null)
            // set the dialog and show.
            val id = view.id
            when(view.id) {
                R.id.etRaceCode -> {
                    raceCodesDilaog = RaceCodesDialog()
                    raceCodesDilaog.show(fragTrans, "race_codes_dialog")
                }
                R.id.etCityCode -> {
                    cityCodesDialog = CityCodesDialog()
                    cityCodesDialog.show(fragTrans, "city_codes_dialog")
                }
                R.id.etRaceNum -> {
                    val bundle = Bundle()
                    bundle.putInt("key", R.integer.npCtxRaceNum)
                    numberPadDialog = NumberPadDialog()
                    numberPadDialog.arguments = bundle
                    numberPadDialog.show(fragTrans, "number_pad_dialog")
                }
                R.id.etRaceSel -> {
                    val bundle = Bundle()
                    bundle.putInt("key", R.integer.npCtxRaceSel)
                    numberPadDialog = NumberPadDialog()
                    numberPadDialog.arguments = bundle
                    numberPadDialog.show(fragTrans, "number_pad_dialog")
                }
            }
            return true
        }
        return false
    }

    /**
     * Setup UI and view model.
     */
    private fun initialiseUI(view: View) {
        // Hide the FAB.
        (activity?.findViewById(R.id.id_fab) as FloatingActionButton).hide()

        // Set the Save button and listener.
        btnSave = view.findViewById<Button>(R.id.id_btn_save)
        btnSave.setOnClickListener(this)

        // Get the toolbar.
        toolBar = activity?.findViewById(R.id.id_toolbar) as Toolbar

        // Get the Race related views.
        etCityCode = view.findViewById(R.id.etCityCode)
        etCityCode.setOnTouchListener(this)
        etCityCode.showSoftInputOnFocus = false  // use dialog.

        etRaceCode = view.findViewById(R.id.etRaceCode)
        etRaceCode.setOnTouchListener(this)
        etCityCode.showSoftInputOnFocus = false  // use dialog.

        etRaceNum = view.findViewById(R.id.etRaceNum)
        etRaceNum.setOnTouchListener(this)
//        etCityCode.showSoftInputOnFocus = false

        etRaceSel = view.findViewById(R.id.etRaceSel)
        etRaceSel.setOnTouchListener(this)
//        etCityCode.showSoftInputOnFocus = false

        etRaceTime = view.findViewById(R.id.etRaceTime)
        etRaceTime.setOnTouchListener(this)
//        etCityCode.showSoftInputOnFocus = false

        // view model.
        raceViewModel = ViewModelProviders.of(activity!!).get(RaceViewModel::class.java)
        raceId = arguments?.getLong(getString(R.string.key_edit_existing))
        raceViewModel.getRaceLD(raceId!!).observe(activity!!, RaceObserver(raceViewModel.getRaceLD(raceId!!), view))
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

    private lateinit var toolBar: Toolbar

    private var raceId: Long? = null
    private lateinit var etCityCode : EditText
    private lateinit var etRaceCode : EditText
    private lateinit var etRaceNum : EditText
    private lateinit var etRaceSel : EditText
    private lateinit var etRaceTime : EditText
    private lateinit var btnSave: Button

    private lateinit var raceViewModel: RaceViewModel

    private var editType: Int? = null

    private lateinit var cityCodesDialog: DialogFragment
    private lateinit var raceCodesDilaog: DialogFragment
    private lateinit var numberPadDialog: DialogFragment

}
