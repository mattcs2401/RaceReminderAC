package mcssoft.com.racereminderac.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus

class NumberPadDialog : DialogFragment(), DialogInterface.OnClickListener, View.OnClickListener {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = activity!!.layoutInflater

        // Get reference to the 'main' view so can set button listeners.
        // Note: onViewCreated() is not called when using the builder.
        rootView = inflater.inflate(R.layout.number_pad, null)
        initialiseViews(rootView)

        var title = ""
        args = arguments!!.getInt(getString(R.string.key_general))
        when(args) {
            R.integer.npCtxRaceNum -> title = getString(R.string.np_title_race_no)
            R.integer.npCtxRaceSel -> title = getString(R.string.np_title_race_sel)
        }

        // build the dialog.
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setTitle(title)
                .setView(rootView)
                .setPositiveButton(R.string.lbl_ok, this)
                .setNegativeButton(R.string.lbl_cancel, this)
        return builder.create()
    }

    /**
     * Listener for the dialog's (custom) number buttons.
     */
    override fun onClick(view: View) {
        // TODO - implement on the fly checking digit validation.
        if(number.isBlank()) {
            // no number previously selected.
            number = (view as Button).text.toString()
        } else {
            // TBA - validation
            if(number.length < 3) {
                number += (view as Button).text.toString()
            }
        }
    }

    /**
     * Listener for the dialog's (standard) OK and Cancel buttons.
     */
    override fun onClick(dialog: DialogInterface?, which: Int) {
        var evntMsg: EventMessage? = null
//        args = arguments?.getInt("key")!!
        when(which) {
            // OK button.
            Dialog.BUTTON_POSITIVE -> {
                if(number.isBlank()) {
                    // only the OK button has been selected.
                    evntMsg = EventMessage("", R.integer.number_pad_dialog_id, args)
                } else {
                    // a number button has previously been selected.
                    evntMsg = EventMessage(number, R.integer.number_pad_dialog_id, args)
                }
                EventBus.getDefault().post(evntMsg)
            }
        }
        this.dialog.dismiss()
    }

    private fun validate() {
        // TODO - validation routine of some sort.
    }

    private fun initialiseViews(view: View) {
        /** Note: 'synthetic' didn't seem to work here ? **/

        btn0 = view.findViewById<Button>(R.id.id_np_btn_0)
        btn0.setOnClickListener(this)
        btn0.isEnabled = false
        //(view.findViewById<Button>(R.id.id_np_btn_0)).setOnClickListener(this)

        (view.findViewById<Button>(R.id.id_np_btn_1)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_2)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_3)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_4)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_5)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_6)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_7)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_8)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_np_btn_9)).setOnClickListener(this)
    }

    private lateinit var btn0: Button
    private lateinit var rootView: View     // dialog's main view.
    private var number: String = ""         // selected button value.
    private var args: Int = 0
}