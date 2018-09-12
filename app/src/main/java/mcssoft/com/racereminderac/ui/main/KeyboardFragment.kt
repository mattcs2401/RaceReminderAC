package mcssoft.com.racereminderac.ui.main

import android.app.Application
import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IKeyboard
import mcssoft.com.racereminderac.utility.RaceKeyboard
import kotlin.coroutines.experimental.coroutineContext

class KeyboardFragment : DialogFragment(), IKeyboard {

    companion object {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.keyboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // this doesn't seem to do much
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        kbdView = view.findViewById<KeyboardView>(R.id.id_kbdView)

        viewId = arguments?.getInt("key_view_id")
        raceKbd = RaceKeyboard(activity!!, kbdView, viewId!!, this, R.xml.dummy_keyboard)
        kbdView.setPreviewEnabled(false)

        when(viewId) {
            R.id.etCityCode -> raceKbd.setLayout(R.xml.city_codes_keyboard)
            R.id.etRaceCode -> raceKbd.setLayout(R.xml.race_codes_keyboard)
            R.id.etRaceNum -> raceKbd.setLayout(R.xml.num_sel_keyboard)
            R.id.etRaceSel -> raceKbd.setLayout(R.xml.num_sel_keyboard)
        }

        raceKbd.show(activity?.findViewById<EditText>(viewId!!)!!)
        val bp = ""
    }

    override fun onFinishKeyboard() {

        val bp = ""
        this.dismiss()
    }

    private var viewId: Int? = null
    private lateinit var kbdView: KeyboardView
    private lateinit var raceKbd: RaceKeyboard
}