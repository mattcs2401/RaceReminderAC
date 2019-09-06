package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R

/**
 * TBA.
 */
class NoNetworkDialog : DialogFragment(), DialogInterface.OnClickListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context!!)
        dialog.setTitle(getString(R.string.title_no_network))
        dialog.setMessage(getString(R.string.message_no_network))
        dialog.setPositiveButton(getString(R.string.lbl_ok), this)
//        dialog.setNegativeButton(getString(R.string.lbl_cancel), this)
        return dialog.create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> {
                this.dialog!!.cancel()
            }
        }
    }
}