package mcssoft.com.racereminderac.ui.main

import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.RaceKeyboard

class KeyBoardFragment : DialogFragment() {

    init {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.keyboard_fragment, container, false)
//        rootView = inflater.inflate(R.layout.keyboard_fragment, container, false)
//        kbdView = rootView.findViewById<KeyboardView>(R.id.id_kbdView)
//        this.arguments = arguments
//        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        kbdView = view.findViewById<KeyboardView>(R.id.id_kbdView)
        args = arguments

        val bp = ""
    }

    private lateinit var rootView: View
    private lateinit var kbdView: KeyboardView
    private var args: Bundle? = null
    private lateinit var raceKbd: RaceKeyboard
}