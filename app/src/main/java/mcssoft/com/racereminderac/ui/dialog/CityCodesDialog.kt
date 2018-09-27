package mcssoft.com.racereminderac.ui.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.city_codes.*
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

        initialiseViews()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_cc_ok -> {
                EventBus.getDefault().post(EventMessage(cityCode, R.integer.city_codes_dialog_id, -1))
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

    private fun initialiseViews() {
        id_btn_A.setOnClickListener(this)
        id_btn_B.setOnClickListener(this)
        id_btn_C.setOnClickListener(this)
        id_btn_D.setOnClickListener(this)
        id_btn_E.setOnClickListener(this)
        id_btn_F.setOnClickListener(this)
        id_btn_I.setOnClickListener(this)
        id_btn_L.setOnClickListener(this)
        id_btn_M.setOnClickListener(this)
        id_btn_N.setOnClickListener(this)
        id_btn_O.setOnClickListener(this)
        id_btn_P.setOnClickListener(this)
        id_btn_Q.setOnClickListener(this)
        id_btn_S.setOnClickListener(this)
        id_btn_T.setOnClickListener(this)
        id_btn_V.setOnClickListener(this)
        id_btn_X.setOnClickListener(this)
        id_btn_Z.setOnClickListener(this)

        btnOk = id_btn_cc_ok
        btnOk.setOnClickListener(this)
        btnOk.isEnabled = false

        btnCancel = id_btn_cc_cancel
        btnCancel.setOnClickListener(this)
    }

    private lateinit var cityCode: String
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
}