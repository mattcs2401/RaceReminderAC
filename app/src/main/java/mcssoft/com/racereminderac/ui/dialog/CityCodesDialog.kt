package mcssoft.com.racereminderac.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.ICodes
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus

class CityCodesDialog : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.city_codes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseViews(view)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_cc_ok -> {
                EventBus.getDefault().post(EventMessage(cityCode, R.integer.city_codes_dialog_id))
                this.dialog.cancel()
            }
            R.id.id_btn_cc_cancel -> {
                this.dialog.cancel()
            }
            else -> {
                cityCode = (view as Button).text.toString()
                btnOk.isEnabled = true
            }
        }
    }

    private fun initialiseViews(view: View) {
        // TBA - other views

        btnOk = view.findViewById<Button>(R.id.id_btn_cc_ok)
        btnOk.setOnClickListener(this)
        btnOk.isEnabled = false

        btnCancel = view.findViewById<Button>(R.id.id_btn_cc_cancel)
        btnCancel.setOnClickListener(this)
    }

    private val OK: Int = -1
    private val CANCEL: Int = -2
    private var cityCode: String = ""
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
}