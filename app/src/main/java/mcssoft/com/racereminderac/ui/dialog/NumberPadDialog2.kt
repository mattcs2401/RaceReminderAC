package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R

class NumberPadDialog2 : DialogFragment(), DialogInterface.OnClickListener {
//https://developer.android.com/reference/android/widget/NumberPicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = activity!!.layoutInflater

        // Get reference to the 'main' view so can set button listeners.
        // Note: onViewCreated() is not called when using the builder.
        rootView = inflater.inflate(R.layout.number_pad2, null)
        initialiseViews(rootView)

//        var title = ""
//        args = arguments!!.getInt(getString(R.string.key_general))
//        when(args) {
//            R.integer.npCtxRaceNum -> title = getString(R.string.np_title_race_no)
//            R.integer.npCtxRaceSel -> title = getString(R.string.np_title_race_sel)
//        }

        // build the dialog.
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context!!)
        builder.setTitle("Dialog Title")
                .setView(rootView)
                .setPositiveButton(R.string.lbl_ok, this)
                .setNegativeButton(R.string.lbl_cancel, this)
        return builder.create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {

    }

    private fun initialiseViews(view: View) {
        raceNoNumPick = view.findViewById(R.id.id_np_race_no)
        raceNoNumPick.minValue = 1
        raceNoNumPick.maxValue = 12
        raceSelNumPick = view.findViewById(R.id.id_np_race_sel)
        raceSelNumPick.minValue = 1
        raceSelNumPick.maxValue = 24
    }

    private lateinit var rootView: View
    private lateinit var raceNoNumPick: NumberPicker
    private lateinit var raceSelNumPick: NumberPicker
}