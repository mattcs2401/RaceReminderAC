package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.utility.eventbus.DeleteAllMessage
import org.greenrobot.eventbus.EventBus

class DeleteAllDialog(context: Context) : DialogFragment(), DialogInterface.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(context!!)
        alertDialogBuilder.setTitle("Delete All")
        alertDialogBuilder.setMessage("Are you sure?")
        alertDialogBuilder.setPositiveButton("OK", this)
        alertDialogBuilder.setNegativeButton("Cancel", this)
        return alertDialogBuilder.create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            DialogInterface.BUTTON_POSITIVE -> {
                EventBus.getDefault().post(DeleteAllMessage())
                this.dialog!!.cancel()
            }
        }
    }
}