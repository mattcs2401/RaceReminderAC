package mcssoft.com.racereminderac.utility

import android.app.Activity
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.Editable
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IKeyboard

class RaceKeyboard(activity: Activity, kbdView: KeyboardView?, viewId: Int, layoutId: Int?) :
        KeyboardView.OnKeyboardActionListener {

    private var activity: Activity? = null
    private var kbdView: KeyboardView? = null     // id of <android.inputmethodservice.KeyboardView>.
    private var viewId: Int = 0                  // id of the component that has the keyboard.
    private var layoutId: Int? = 0               // e.g. R.xml.layout
    private var keyBoard: Keyboard? = null

    init {
        /* This still needs work, especially when swapping keyboard layouts. */
        this.activity = activity
        this.kbdView = kbdView
        this.viewId = viewId
        this.layoutId = layoutId

        keyBoard = Keyboard(activity, layoutId!!)
        kbdView?.keyboard = keyBoard

        kbdView?.setPreviewEnabled(false)
        kbdView?.setOnKeyboardActionListener(this)

    }

    override fun onKey(keyCode: Int, keyCodes: IntArray?) {
        val view = activity?.getWindow()?.currentFocus

        if (view == null) { // view.javaClass != EditText::class.java) {
            return
        } else {
            editText = view as EditText
            editable = editText!!.getText()
            val start = editText!!.getSelectionStart()

            when (keyCode) {
                R.integer.keycode_delete -> delete(start)
                R.integer.keycode_cancel -> done(keyCode, this.etSavedVal!!)
                R.integer.keycode_done -> done(keyCode, "")
                else -> editable?.insert(start, Character.toString(keyCode.toChar()))
            }//hide();
        }
    }

    fun setLayout(layoutId: Int) {
        this.layoutId = layoutId
        kbdView!!.keyboard = Keyboard(activity, layoutId)
    }

    fun show(view: View?) {
        //        Log.d(LOG_TAG, "show");
        kbdView?.setVisibility(View.VISIBLE)
        kbdView?.setEnabled(true)
//        onKeyboard(true)

//        if (view != null) {
//        (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
//            .hideSoftInputFromWindow(view?.windowToken, 0)
//            val viewId = view.id
//            val et = view.findViewById<View>(viewId) as EditText
//            etSavedVal = et.text.toString()
//
//            when (viewId) {
//                R.id.etRaceNum -> compId = R.id.etRaceNum
//                R.id.etRaceSel -> compId = R.id.etRaceSel
//            }
//        }
        val bp = ""
    }

    fun hide() {
        //        Log.d(LOG_TAG, "hide");
        kbdView?.setVisibility(View.GONE)
        kbdView?.setEnabled(false)
        onKeyboard(false)
    }

    /**
     * Hide or show the Cancel and Save buttons on the host screen when the soft keyboard displays.
     * @param hide True - hide host screen buttons, else, show buttons (or do nothing if no buttons given).
     */
    // TODO - make this generic to any control.
    private fun onKeyboard(hide: Boolean) {
        if (buttons == null || buttons!!.size == 0) {
            return
        } else if (hide) {
            for (btn in buttons!!) {
                btn.setVisibility(View.GONE)
            }
        } else {
            for (btn in buttons!!) {
                btn.setVisibility(View.VISIBLE)
            }
        }
    }

    private fun delete(start: Int) {
        if (editable != null && start > 0) {
            editable!!.delete(start - 1, start)
        }
    }

    private fun done(keyCode: Int, etSavedVal: String) {
        if (keyCode == R.integer.keycode_cancel) {
            // If CANCL, restore previous "entry" value.
            editText?.setText(etSavedVal)
            highLight(false)
            hide()
        } else if (editable?.length == 0) {
            // If DONE but no value, then show toast and highlight.
            Toast.makeText(activity?.getApplicationContext(), R.string.lbl_value_required, Toast.LENGTH_SHORT).show()
            highLight(true)
        } else {
            // Edit is valid.
            highLight(false)
//            val ifk = activity as IKeyboard
//            ifk.onFinishKeyboard(compId, keyCode)
            hide()
        }
    }

    private fun highLight(highlighted: Boolean) {
        // Need to get padding, setting new drawable erases old values.
        val left = editText?.getPaddingLeft()
        val top = editText?.getPaddingTop()
        val right = editText?.getPaddingRight()
        val bottom = editText?.getPaddingBottom()

        if (highlighted) {
            editText?.setBackgroundResource(R.drawable.et_basic_red_outline)
        } else {
            editText?.setBackgroundResource(R.drawable.et_basic)
        }

        editText?.setPadding(left!!, top!!, right!!, bottom!!)
    }

    private fun setKeyboard(view: View) {
//        when(view.id) {
//            R.id.etRaceNum -> {
//                setLayout(R.xml.num_sel_keyboard)
//                show(view)
//            }
//        }
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    //    private View view;            // the view of the component.

    private var editable: Editable? = null    // component's editor.
    private var editText: EditText? = null    // the component that currently has the keyboard.
    private var buttons: Array<Button>? = null    //
    private var etSavedVal: String? = null    // value of the component when keyboard first displays.

//    private val LOG_TAG = this.javaClass.canonicalName
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Not used ATT (but need to include).">
    override fun onPress(primaryCode: Int) {}

    override fun onRelease(primaryCode: Int) {}

    override fun onText(text: CharSequence) {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeDown() {}

    override fun swipeUp() {}
    //</editor-fold>
}