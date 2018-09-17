package mcssoft.com.racereminderac.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.ICodes


class CityCodesDialog : DialogFragment(), View.OnClickListener,  DialogInterface.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("City Codes")
                .setView(R.layout.city_codes)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", this)
        return builder.create()
    }

    override fun onClick(view: View) {
        viewVal = (view as Button).text.toString()
//        when(view.id) {
//            R.id.id_rc_btn_R -> { viewVal = (view as Button).text.toString() }
//            R.id.id_rc_btn_T -> {}
//            R.id.id_rc_btn_G -> {}
//            R.id.id_rc_btn_S -> {}
//        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            OK -> {
                if(viewVal != null) {
                    (activity as ICodes).onFinishCityCodes(viewVal!!)
                    this.dialog.cancel()
                } else {
                    // TBA
                }

            }
            CANCEL -> { this.dialog.cancel() }
        }
        val bp = ""
    }

    private var viewVal: String? = null
    private val OK: Int = -1
    private val CANCEL: Int = -2
}