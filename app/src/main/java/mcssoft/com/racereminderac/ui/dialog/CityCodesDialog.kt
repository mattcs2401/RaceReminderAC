package mcssoft.com.racereminderac.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus

class CityCodesDialog : DialogFragment(), DialogInterface.OnClickListener, View.OnClickListener {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = activity!!.layoutInflater

        // Get reference to the 'main' view so can set button listeners.
        // Note: onViewCreated() is not called when using the builder.
        rootView = inflater.inflate(R.layout.city_codes, null)
        initialiseViews(rootView)

        // build the dialog.
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setTitle(R.string.cc_title)
                .setView(rootView)
                .setPositiveButton(R.string.lbl_ok, this)
                .setNegativeButton(R.string.lbl_cancel, this)
                .setOnDismissListener(this)
        return builder.create()
    }

    override fun onClick(view: View) {
        cityCode = (view as Button).text.toString()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        var evntMsg: EventMessage? = null
        when(which) {
            Dialog.BUTTON_POSITIVE -> {
                if(cityCode == null) {
                    evntMsg = EventMessage("", R.integer.city_codes_dialog_id, R.integer.ctxNoCityCode)
                } else {
                    evntMsg = EventMessage(cityCode!!, R.integer.city_codes_dialog_id, -1)
                }
                EventBus.getDefault().post(evntMsg)
            }
        }
        this.dialog.dismiss()
    }

    private fun initialiseViews(view: View) {
        // Note: 'synthetic' didn't seem to work ?
        (view.findViewById<Button>(R.id.id_cc_btn_A)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_B)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_C)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_D)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_E)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_F)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_I)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_L)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_M)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_N)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_O)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_P)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_Q)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_S)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_T)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_V)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_X)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_cc_btn_Z)).setOnClickListener(this)
    }

    private lateinit var rootView: View
    private var cityCode: String? = null
}