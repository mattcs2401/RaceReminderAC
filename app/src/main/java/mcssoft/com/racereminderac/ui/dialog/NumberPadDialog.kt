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
import kotlinx.android.synthetic.main.number_pad.*
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

        // build the dialog.
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Enter a number")
                .setView(rootView)
                .setPositiveButton(R.string.lbl_ok, this)
                .setNegativeButton(R.string.lbl_cancel, this)
        return builder.create()
    }

    override fun onClick(view: View) {
        number = (view as Button).text.toString()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        var evntMsg: EventMessage? = null
        when(which) {
            Dialog.BUTTON_POSITIVE -> {
                if(number == null) {
                    evntMsg = EventMessage("", R.integer.number_pad_dialog_id, R.integer.ctxNoNumber)
                } else {
                    arguments?.getInt("key")
                    evntMsg = EventMessage(number!!, R.integer.number_pad_dialog_id, arguments!!.getInt("key"))
                }
                EventBus.getDefault().post(evntMsg)
            }
        }
        this.dialog.dismiss()
    }

    private fun initialiseViews(view: View) {
        // Note: 'synthetic' didn't seem to work here ?
        (view.findViewById<Button>(R.id.id_btn_0)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_1)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_2)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_3)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_4)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_5)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_6)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_7)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_8)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_9)).setOnClickListener(this)
    }

    private lateinit var rootView: View     // dialog's main view.
    private var number: String? = null      // selected button value.
}