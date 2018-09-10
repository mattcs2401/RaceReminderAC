package mcssoft.com.racereminderac.ui.main

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.RaceKeyboard

class KeyboardFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.keyboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        kbdView = view.findViewById<KeyboardView>(R.id.id_kbdView)
        args = arguments
        val viewId = arguments?.getInt("key_view_id")
        when(viewId) {
            R.id.etRaceNum -> {
                raceKbd = RaceKeyboard(activity!!, kbdView, viewId, R.xml.num_sel_keyboard)
                raceKbd.show(activity?.findViewById(viewId))
            }
//            R.id.etRaceSel -> {
//                kbdView.keyboard = Keyboard(activity, R.xml.num_sel_keyboard)
//            }
        }

        val bp = ""
    }

    private lateinit var rootView: View
    private lateinit var kbdView: KeyboardView
    private var args: Bundle? = null
    private lateinit var raceKbd: RaceKeyboard
}