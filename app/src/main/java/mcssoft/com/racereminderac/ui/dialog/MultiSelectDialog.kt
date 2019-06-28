package mcssoft.com.racereminderac.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.multisel_fragment.view.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.eventbus.MultiSelMessage
import org.greenrobot.eventbus.EventBus

class MultiSelectDialog(context: Context) : DialogFragment(), DialogInterface.OnDismissListener,
        View.OnClickListener {

    // Notes: Decided not to use the dialog builder as we need to use elements of the standard
    //        fragment lifecycle, i.e. onCreateView as we are using a custom view.

    // TODO - enable/disable Add/Remove buttons depending on count.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.multisel_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseUI(view)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_multi_sel_add -> {
                doAdd()
            }
            R.id.id_btn_multi_sel_remove -> {
                doRemove()
            }
            R.id.id_btn_multi_sel_ok -> {
                // check something selected.
                if(!isSelectsEmpty()) {
                    EventBus.getDefault().post(MultiSelMessage(selArray))
                } else {
                    EventBus.getDefault().post(MultiSelMessage(null))
                }
                this.dismiss()
            }
            R.id.id_btn_multi_sel_cancel -> {
                EventBus.getDefault().post(MultiSelMessage(null))
                this.dismiss()
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Utility">
    /**
     * Utility method, add the selected value to the list of selected items.
     */
    private fun doAdd() {
        if(count < Constants.SELECTS_MAX) {
//            if(!btnAdd.isEnabled) {
//                btnAdd.isEnabled = true
//            }
            val selValue = rsVals[npRaceSel.value]
            if (!valExists(selValue)) {
                when (count) {
                    0 -> {
                        tvSel0.text = selValue
                        selArray[0] = selValue
                    }
                    1 -> {
                        tvSel1.text = selValue
                        selArray[1] = selValue
                    }
                    2 -> {
                        tvSel2.text = selValue
                        selArray[2] = selValue
                    }
                    3 -> {
                        tvSel3.text = selValue
                        selArray[3] = selValue
                    }
                }
                count += 1
            }
        } else {
            btnAdd.isEnabled = false
        }
    }

    /**
     * Utility method, remove the last selected value from view.
     */
    private fun doRemove() {
        if(!isSelectsEmpty()) {
            // get last non-null.
            val ndx = getLast()
            if (ndx != -1) {
                // update view and backing array.
                updateViewRemove(ndx)
                selArray[ndx] = ""
                // reset count.
                count -= 1
            }
        }
    }

    /**
     * Utility method, quick and dirty check that the backing array's 1st element is null.
     */
    private fun isSelectsEmpty() : Boolean = selArray[0] == ""

    /**
     * Utility method, get the last element in the backing array that is not null.
     * @return The array index, or -1.
     */
    private fun getLast() : Int {
        for(ndx in 3 downTo 0) {
            if (selArray[ndx] != "") {
                return ndx
            }
        }
        return -1
    }

    /**
     * Utility method, set the text of the select TextView to null.
     * @param ndx: An index value corresponding to the TextView.
     */
    private fun updateViewRemove(ndx: Int) {
        when(ndx) {
            0 -> {tvSel0.text = ""}
            1 -> {tvSel1.text = ""}
            2 -> {tvSel2.text = ""}
            3 -> {tvSel3.text = ""}
        }
    }

    /**
     * Utility method, check if the selected value already exists in the backing array.
     * @param value: The value to check for.
     * @return True if the given value exists, else false.
     */
    private fun valExists(value: String) : Boolean {
        for(ndx in 0..3) {
            if((selArray[ndx] == value)) {
                return true
            }
        }
        return false
    }

    private fun initialiseUI(view: View) {
        tvSel0 = view.id_tv_multi_sel_1
        tvSel1 = view.id_tv_multi_sel_2
        tvSel2 = view.id_tv_multi_sel_3
        tvSel3 = view.id_tv_multi_sel_4

        btnAdd = view.id_btn_multi_sel_add
        btnAdd.setOnClickListener(this)
        btnRemove = view.id_btn_multi_sel_remove
        btnRemove.setOnClickListener(this)
        btnOk = view.id_btn_multi_sel_ok
        btnOk.setOnClickListener(this)
        btnCancel = view.id_btn_multi_sel_cancel
        btnCancel.setOnClickListener(this)

        rsVals = resources.getStringArray(R.array.raceSel)

        npRaceSel = view.id_np_multi_sel
        npRaceSel.minValue = 0
        npRaceSel.maxValue = rsVals.size - 1
        npRaceSel.displayedValues = rsVals
        npRaceSel.wrapSelectorWheel = true
//        npRaceSel.setOnClickListener(this)

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Region: Private Vars">
    private lateinit var tvSel0: TextView
    private lateinit var tvSel1: TextView
    private lateinit var tvSel2: TextView
    private lateinit var tvSel3: TextView

    private lateinit var btnAdd: ImageButton
    private lateinit var btnRemove: ImageButton
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
    private lateinit var npRaceSel: NumberPicker

    private lateinit var rsVals: Array<String>
    private var selArray = arrayOf<String>("","","","")
    private var count: Int = 0
    //</editor-fold>
}