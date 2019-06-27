package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R

class MultiSelectDialog(context: Context) : DialogFragment(), DialogInterface.OnDismissListener,
        View.OnClickListener {
    // Notes: Decided not to use the dialog builder as we need to use elements of the standard
    // fragment lifecycle.

    // TODO - EventBus message.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.multisel_fragment,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bp=""
    }

    override fun onStart() {
        super.onStart()
        val bp=""
    }

    override fun onClick(view: View?) {
        // TBA - For the Add button.

    }

    // Note: This is also called if the user selects something not on the dialog, i.e. the
    //       underlying fragment.
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // TBA - if no selections collated, use EventBus to tell the EditFragment to clear the multi
        //       select checkbox.
        val bp=""

    }

    private fun initialiseUI() {

    }

    private lateinit var layoutView: View
}