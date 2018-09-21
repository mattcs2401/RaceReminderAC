package mcssoft.com.racereminderac.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.preference.DialogPreference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.ICodes
import mcssoft.com.racereminderac.utility.MessageEvent
import org.greenrobot.eventbus.EventBus
import android.view.Window.FEATURE_NO_TITLE




class RaceCodesDialog : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.race_codes2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.findViewById<Button>(R.id.id_rc_btn_R)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_T)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_G)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_S)).setOnClickListener(this)
        (view.findViewById<Button>(R.id.id_rc_btn_cancel)).setOnClickListener(this)
        btnOK = view.findViewById<Button>(R.id.id_rc_btn_ok)
        btnOK.setOnClickListener(this)
        btnOK.isEnabled = false
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_rc_btn_ok -> {
                EventBus.getDefault().post(MessageEvent(raceCode))
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

    private val OK: Int = -1
    private val CANCEL: Int = -2
    private lateinit var btnOK: Button
    private var raceCode: String = ""
}