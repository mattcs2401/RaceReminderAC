package mcssoft.com.racereminderac.utility.callback

import android.util.Log
import androidx.activity.OnBackPressedCallback

class BackPressCB(enabled: Boolean) : OnBackPressedCallback(enabled) {

    override fun handleOnBackPressed() {
        // do nothing
//        Log.d("tag","BackPressCB.handleOnBackPressed")
    }

    fun removeCallback() {
        super.remove()
//        Log.d("tag","BackPressCB.removeCallback")
    }
}