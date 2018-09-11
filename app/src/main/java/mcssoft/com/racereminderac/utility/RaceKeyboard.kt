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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IKeyboard

class RaceKeyboard(activity: Activity, kbdView: KeyboardView?, viewId: Int, layoutId: Int?) :
        KeyboardView.OnKeyboardActionListener {

    private var activity: Activity? = null
    private var kbdView: KeyboardView? = null     // id of <android.inputmethodservice.KeyboardView>.
    private var viewId: Int = 0                  // id of the component that has the keyboard.
    private var layoutId: Int? = 0               // e.g. R.xml.layout
    private var keyBoard: Keyboard? = null
    private var etSavedVal: String? = null    // value of the component when keyboard first displays.

    init {
        /* This still needs work, especially when swapping keyboard layouts. */
        this.activity = activity
        this.kbdView = kbdView
        this.viewId = viewId
        this.layoutId = layoutId

        if(layoutId == null) {
            /* Secondary constructor doesn't have 'layoutId' as a parameter but still calls through
             primary. */
            keyBoard = Keyboard(activity, R.xml.dummy_keyboard)
        } else {
            keyBoard = Keyboard(activity, layoutId)
        }

        kbdView?.keyboard = keyBoard
        kbdView?.setPreviewEnabled(false)
        kbdView?.setOnKeyboardActionListener(this)
        etSavedVal = (activity.findViewById<EditText>(viewId)).text.toString()
//        val bp = ""
    }

    // secondary constructor.
    constructor(activity: Activity, kbdView: KeyboardView?, viewId: Int?) : this(activity, kbdView, viewId!!, null) {
        this.activity = activity
        this.kbdView = kbdView
        this.viewId = viewId
    }

    override fun onKey(keyCode: Int, keyCodes: IntArray?) {
//        val view = activity?.getWindow()?.currentFocus
//        val view = activity?.findViewById<EditText>(viewId)
//
//        if (view != null) { // view.javaClass != EditText::class.java) {
//            editable = view.text
//            etSavedVal = editable.toString()
//            val start = view.selectionStart
//
//            when (keyCode) {
//                R.integer.keycode_delete -> delete(start)
//                R.integer.keycode_cancel -> done(keyCode, this.etSavedVal!!)
//                R.integer.keycode_done -> done(keyCode, "")
//                else -> editable?.insert(start, Character.toString(keyCode.toChar()))
//            }
//            //hide();
//        } else {
//            return
//        }
    }

    /**
     * Set the layout associated with the keyboard.
     * @param layoutId The id of the xml layout, e.g. R.xml.layout_name.
     * @Note Must be called if secondary constructor is being used.
     **/
    fun setLayout(layoutId: Int) {
        this.layoutId = layoutId
        kbdView!!.keyboard = Keyboard(activity, layoutId)
    }

    fun show(view: View?) {
        //        Log.d(LOG_TAG, "show");
        kbdView?.setVisibility(View.VISIBLE)
        kbdView?.setEnabled(true)

        (activity?.findViewById<EditText>(viewId))?.requestFocus()

        onKeyboard(true)
        val bp = ""
    }

    fun hide() {
        //        Log.d(LOG_TAG, "hide");
        kbdView?.setVisibility(View.GONE)
        kbdView?.setEnabled(false)
        onKeyboard(false)
        //(activity?.getFragmentManager()?.findFragmentById(R.id.id_edit_fragment) as IKeyboard).onFinishKeyboard()
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
        when(keyCode) {
            cancel -> {
                // If CANCL, restore previous "entry" value.
                editText?.setText(etSavedVal)
                highLight(false)
                hide()
            }
            done -> {
                if (editable?.length == 0) {
                    Toast.makeText(activity?.getApplicationContext(), R.string.lbl_value_required, Toast.LENGTH_SHORT).show()
                    highLight(true)
                } else {
                    // assume valid
                    highLight(false)
                    hide()
                }
            }
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

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private var editable: Editable? = null    // component's editor.
    private var editText: EditText? = null    // the component that currently has the keyboard.
    private var buttons: Array<Button>? = null    //

    private val cancel: Int = -3
    private val done: Int = -4
//    private val LOG_TAG = this.javaClass.canonicalName
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: OnKeyboardActionListener.">
    override fun onPress(keyCode: Int) {
        /* Note: this is called before onKey(). */
        when (keyCode) {
            // Note: using R.integer.xxx didn't seem to work here.
            cancel -> done(keyCode, this.etSavedVal!!)
            done -> done(keyCode, "")
        }

        val bp = ""
    }

    override fun onRelease(primaryCode: Int) {}

    override fun onText(text: CharSequence) {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeDown() {}

    override fun swipeUp() {}
    //</editor-fold>
}