package mcssoft.com.racereminderac.ui.main

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
import mcssoft.com.racereminderac.utility.RaceKeyboard

class KeyboardFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.keyboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // this doesn't seem to do much
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        kbdView = view.findViewById<KeyboardView>(R.id.id_kbdView)

        val viewId = arguments?.getInt("key_view_id")
        raceKbd = RaceKeyboard(activity!!, kbdView, viewId)

        when(viewId) {
            R.id.etCityCode -> raceKbd.setLayout(R.xml.city_codes_keyboard)
            R.id.etRaceCode -> raceKbd.setLayout(R.xml.race_codes_keyboard)
            R.id.etRaceNum -> raceKbd.setLayout(R.xml.num_sel_keyboard)
            R.id.etRaceSel -> raceKbd.setLayout(R.xml.num_sel_keyboard)
        }

        raceKbd.show(activity?.findViewById<EditText>(viewId!!))
    }

    private lateinit var kbdView: KeyboardView
    private lateinit var raceKbd: RaceKeyboard
}