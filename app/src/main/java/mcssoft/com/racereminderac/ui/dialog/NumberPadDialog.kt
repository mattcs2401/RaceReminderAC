package mcssoft.com.racereminderac.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.utility.EventMessage
import mcssoft.com.racereminderac.R
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
        // basically just hand off to the validation functions.
        validate(view)
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

    private fun validate(view: View) {
        val nbr: Int = ((view as Button).text.toString()).toInt()
        when(args) {
            // Only max 12.
            R.integer.npCtxRaceNum -> validateRaceNo(view)
            // Only mas 24.
            R.integer.npCtxRaceSel -> validateRaceSel(view)
        }
    }

    private fun validateRaceNo(view: View) {
        val nbr: Int = ((view as Button).text.toString()).toInt()
        if(number.isBlank()) {
            // Nothing entered as yet.
            number = nbr.toString()
            view.setBackgroundResource(R.drawable.tv_np_bkgnd_on_sel)
            if(nbr == 1) {
                btn0.isEnabled = true
            }
        } else {
            if (nbr in arrayOf(0,1,2)) {
                number += number + nbr.toString()
                view.setBackgroundResource(R.drawable.tv_np_bkgnd_on_sel)
            }

        }
    }

    private fun validateRaceSel(view: View) {

    }

    private fun initialiseViews(view: View) {
        /** Note: 'synthetic' didn't seem to work here ? **/

        btn0 = view.findViewById(R.id.id_np_btn_0)
        btn0.setOnClickListener(this)
        btn0.isEnabled = false

        btn1 = view.findViewById(R.id.id_np_btn_1)
        btn1.setOnClickListener(this)

        btn2 = view.findViewById(R.id.id_np_btn_2)
        btn2.setOnClickListener(this)

        btn3 = view.findViewById(R.id.id_np_btn_3)
        btn3.setOnClickListener(this)

        btn4 = view.findViewById(R.id.id_np_btn_4)
        btn4.setOnClickListener(this)

        btn5 = view.findViewById(R.id.id_np_btn_5)
        btn5.setOnClickListener(this)

        btn6 = view.findViewById(R.id.id_np_btn_6)
        btn6.setOnClickListener(this)

        btn7 = view.findViewById(R.id.id_np_btn_7)
        btn7.setOnClickListener(this)

        btn8 = view.findViewById(R.id.id_np_btn_8)
        btn8.setOnClickListener(this)

        btn9 = view.findViewById(R.id.id_np_btn_9)
        btn9.setOnClickListener(this)

    }

    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button

    private lateinit var rootView: View     // dialog's main view.
    private var number: String = ""         // selected button value.
    private var args: Int = 0
}