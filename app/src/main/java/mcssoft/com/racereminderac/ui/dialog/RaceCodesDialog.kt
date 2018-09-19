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


class RaceCodesDialog : DialogFragment(), View.OnClickListener,  DialogInterface.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Race Codes") // <-- this can be a custom view if req.
                .setView(R.layout.race_codes)
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", this)
        return builder.create()
    }

    override fun onClick(view: View) {
        raceCode = (view as Button).text.toString()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            OK -> {
                if(!raceCode.isEmpty()) {
                    (activity as ICodes.IRaceCodes).onFinishRaceCodes(raceCode)
                    this.dialog.cancel()
                }
            }
            CANCEL -> { this.dialog.cancel() }
        }
    }

    private val OK: Int = -1
    private val CANCEL: Int = -2
    private var raceCode: String = ""
}