package mcssoft.com.racereminder.interfaces

import android.view.View

/**
 * Interface between the EditFragment and EditActivity to show the codes fragments and edit results.
 */
interface IShowCodes {
    /**
     * Call back to xxx.
     * @param fragId The codes fragment identifier.
     * @param view Used to instantiate a particular view (EditText).
     */
    fun onShowCodes(fragId: Int, view: View)

    /**
     * Call back to return the selected code from the particular codes fragment.
     * @param fragId The codes fragment identifier.
     * @param code The code seleted.
     */
    fun onFinishCodes(fragId: Int, code: String)

    /**
     * @param result The collated results of the edit.
     */
    fun onFinish(result: Array<String>)
}