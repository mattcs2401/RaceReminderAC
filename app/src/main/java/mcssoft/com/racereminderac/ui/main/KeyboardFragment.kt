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

class KeyboardFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.keyboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // this doesn't seem to do much
        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        kbdView = view.findViewById<KeyboardView>(R.id.id_kbdView)

        val viewId = arguments?.getInt("key_view_id")
        raceKbd = RaceKeyboard(activity!!, /*kbdView, viewId*/ R.xml.dummy_keyboard)
//
//        when(viewId) {
//            R.id.etCityCode -> raceKbd.setLayout(R.xml.city_codes_keyboard)
//            R.id.etRaceCode -> raceKbd.setLayout(R.xml.race_codes_keyboard)
//            R.id.etRaceNum -> raceKbd.setLayout(R.xml.num_sel_keyboard)
//            R.id.etRaceSel -> raceKbd.setLayout(R.xml.num_sel_keyboard)
//        }
//
//        raceKbd.show(activity?.findViewById<EditText>(viewId!!))
        val bp = ""
    }

    private class RaceKeyboard(context: Context, layoutId: Int?) : Keyboard(context, layoutId!!), KeyboardView.OnKeyboardActionListener {

        private val context: Context

        init {
            this.context = context
        }

        private fun done(keyCode: Int, etSavedVal: String) {
            val bp = ""
        }

        override fun onPress(keyCode: Int) {
            when (keyCode) {
                // Note: using R.integer.xxx didn't seem to work here.
                cancel -> done(keyCode, this.etSavedVal)
                done -> done(keyCode, "")
            }
            (context.getFragmentManager()?.findFragmentById(R.id.id_edit_fragment) as IKeyboard).onFinishKeyboard()
            val bp = ""
        }

        override fun onRelease(p0: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun swipeLeft() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun swipeRight() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun swipeUp() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun swipeDown() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onKey(p0: Int, p1: IntArray?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onText(p0: CharSequence?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private val etSavedVal: String = ""
        private val cancel: Int = -3
        private val done: Int = -4

    }

    private lateinit var kbdView: KeyboardView
    private lateinit var raceKbd: RaceKeyboard
}