package mcssoft.com.racereminderac.ui.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.race_codes.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus

class RaceCodesDialog : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.race_codes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set the listeners.
        id_rc_btn_R.setOnClickListener(this)
        id_rc_btn_T.setOnClickListener(this)
        id_rc_btn_G.setOnClickListener(this)
        id_rc_btn_S.setOnClickListener(this)
        id_rc_btn_cancel.setOnClickListener(this)

        // special case for the OK button.
        btnOK = id_rc_btn_ok
        btnOK.setOnClickListener(this)
        btnOK.isEnabled = false
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_rc_btn_ok -> {
                EventBus.getDefault().post(EventMessage(raceCode, R.integer.race_codes_dialog_id, -1))
                this.dialog.cancel()
            }
            R.id.id_rc_btn_cancel -> {
                this.dialog.cancel()
            }
            else -> {
                raceCode = (view as Button).text.toString()
                btnOK.isEnabled = true
            }
        }
    }

    private lateinit var raceCode: String
    private lateinit var btnOK: Button
}