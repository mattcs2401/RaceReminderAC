package mcssoft.com.racereminderac.ui.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import mcssoft.com.racereminderac.R
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
                if(!btnOk.isEnabled) {
                    btnOk.isEnabled = true
                }
            }
        }
    }

    private fun initialiseViews(view: View) {
        (view.findViewById<Button>(R.id.id_btn_A)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_B)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_C)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_D)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_E)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_F)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_I)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_L)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_M)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_N)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_O)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_P)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_Q)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_S)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_T)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_V)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_X)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_btn_Z)).setOnClickListener(this)

        btnOk = view.findViewById<Button>(R.id.id_btn_cc_ok)
        btnOk.setOnClickListener(this)
        btnOk.isEnabled = false

        btnCancel = view.findViewById<Button>(R.id.id_btn_cc_cancel)
        btnCancel.setOnClickListener(this)
    }

    private var cityCode: String = ""
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
}