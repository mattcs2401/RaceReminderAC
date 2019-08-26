package mcssoft.com.racereminderac.utility

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import mcssoft.com.racereminderac.ui.dialog.DeleteAllDialog
import mcssoft.com.racereminderac.ui.dialog.MultiSelectDialog
import mcssoft.com.racereminderac.ui.dialog.NetworkDialog
import mcssoft.com.racereminderac.ui.dialog.TimePickDialog

class DialogManager {
    /**
     * For Singleton instance.
     * @Note: Can't use Context here, so context passed into the methods.
     */
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

    fun showDialog(name: String, args: Bundle, fragTrans: FragmentTransaction, context: Context) {
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
