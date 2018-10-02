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
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus

class RaceCodesDialog : DialogFragment(), DialogInterface.OnClickListener, View.OnClickListener {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflator: LayoutInflater = activity!!.layoutInflater

        // Get reference to the 'main' view so can set button listeners.
        // Note: onViewCreated() is not called when using the builder.
        rootView = inflator.inflate(R.layout.race_codes, null)
        initialiseViews(rootView)

        // build the dialog.
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setTitle(R.string.rc_title)
                .setView(rootView)
                .setPositiveButton(R.string.lbl_ok, this)
                .setNegativeButton(R.string.lbl_cancel, this)
                .setOnDismissListener(this)
        return builder.create()
    }

    override fun onClick(view: View) {
        raceCode = (view as Button).text.toString()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        var evntMsg: EventMessage? = null
        when(which) {
            Dialog.BUTTON_POSITIVE -> {
                if(raceCode == null) {
                    evntMsg = EventMessage("", R.integer.race_codes_dialog_id, R.integer.ctxNoRaceCode)
                } else {
                    evntMsg = EventMessage(raceCode!!, R.integer.race_codes_dialog_id, -1)
                }
                EventBus.getDefault().post(evntMsg)
            }
        }
        this.dialog.dismiss()
    }

    private fun initialiseViews(view: View) {
        // Note: 'synthetic' didn't seem to work ?
        (view.findViewById<Button>(R.id.id_rc_btn_R)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_T)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_G)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_S)).setOnClickListener(this)
    }

    private lateinit var rootView: View
    private var raceCode: String? = null
}