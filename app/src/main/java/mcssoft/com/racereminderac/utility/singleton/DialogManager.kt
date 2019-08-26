package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import mcssoft.com.racereminderac.ui.dialog.DeleteAllDialog
import mcssoft.com.racereminderac.ui.dialog.MultiSelectDialog
import mcssoft.com.racereminderac.ui.dialog.NetworkDialog
import mcssoft.com.racereminderac.ui.dialog.TimePickDialog
import mcssoft.com.racereminderac.utility.Constants

class DialogManager {

    companion object {
        @Volatile private var instance: DialogManager? = null

        fun getInstance(): DialogManager? {
            if (instance == null) {
                synchronized(DialogManager::class) {
                    instance = DialogManager()
                }
            }
            return instance
        }
    }

    fun showDialog(name: String, args: Bundle, fragTrans: FragmentTransaction) {
        var dialog = DialogFragment()
        when(name) {
            Constants.D_DELETE_ALL -> {
                dialog = DeleteAllDialog()
            }
            Constants.D_MULTI_SEL -> {
                dialog = MultiSelectDialog()
            }
            Constants.D_NETWORK -> {
                dialog = NetworkDialog()
            }
            Constants.D_TIMER_PICK -> {
                // Time Picker dialog.
                dialog = TimePickDialog()
            }
        }
        dialog.arguments = args
// TBA        fragTrans.addToBackStack(null)
        dialog.show(fragTrans, name)

    }
}
