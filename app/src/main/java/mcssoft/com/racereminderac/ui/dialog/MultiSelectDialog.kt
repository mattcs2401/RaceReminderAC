package mcssoft.com.racereminderac.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R

class MultiSelectDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.multisel_fragment, container, false)
    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val alertDialogBuilder = AlertDialog.Builder(context!!)
//        alertDialogBuilder.setTitle("Multi Select Enabled")
//        alertDialogBuilder.setMessage(buildDialogText())
//        return alertDialogBuilder.create()
//    }

//    private fun buildDialogText() : String {
//        val lineSep = getString(R.string.line_sep)
//        val sb = StringBuilder()
//        sb.append(getString(R.string.multi_sel_line_1))
//        sb.append(System.getProperty(lineSep))
//        sb.append(System.getProperty(lineSep))
//        sb.append(getString(R.string.multi_sel_line_2))
//        return sb.toString()
//    }
}