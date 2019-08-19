package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.eventbus.DeleteAllMessage
import org.greenrobot.eventbus.EventBus

class NetworkDialog : DialogFragment(), DialogInterface.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context!!)
        dialog.setIcon(R.drawable.ic_action_warning)
                .setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", this)
        return dialog.create()
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        when (which) {
            Dialog.BUTTON_POSITIVE -> {
            }
        }
    }
}