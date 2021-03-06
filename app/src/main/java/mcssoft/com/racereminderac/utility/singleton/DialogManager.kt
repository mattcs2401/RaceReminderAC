package mcssoft.com.racereminderac.utility.singleton

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import mcssoft.com.racereminderac.ui.dialog.DeleteAllDialog
import mcssoft.com.racereminderac.ui.dialog.MultiSelectDialog
import mcssoft.com.racereminderac.ui.dialog.NoNetworkDialog
import mcssoft.com.racereminderac.ui.dialog.TimePickDialog
import mcssoft.com.racereminderac.utility.Constants

/**
 * Utility type class to show various dialogs.
 */
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

    /**
     * Show a dialog.
     * @param name: The dialog tag (from Constants).
     * @param args: Optional arguments to pass to the dialog.
     * @param fragTrans: The transaction manager.
     */
    fun showDialog(name: String, args: Bundle?, fragTrans: FragmentTransaction) {
        var dialog = DialogFragment()
        when(name) {
            Constants.DIALOG_DELETE_ALL -> {
                dialog = DeleteAllDialog()
            }
            Constants.DIALOG_MULTI_SEL -> {
                dialog = MultiSelectDialog()
            }
            Constants.DIALOG_NO_NETWORK -> {
                dialog = NoNetworkDialog()
            }
            Constants.DIALOG_TIMER_PICK -> {
                // Time Picker dialog.
                dialog = TimePickDialog()
            }
        }
        dialog.arguments = args
// TBA        fragTrans.addToBackStack(null)
        dialog.show(fragTrans, name)

    }
}
