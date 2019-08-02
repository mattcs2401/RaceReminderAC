package mcssoft.com.racereminderac.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.extras_fragment.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.eventbus.ExtrasMessage
import mcssoft.com.racereminderac.utility.eventbus.MultiSelMessage
import org.greenrobot.eventbus.EventBus

/**
 * Utility class to display a dialog from which the user can make additional selections for trainer,
 * horse name and jockey.
 */
class ExtrasDialog : DialogFragment(), DialogInterface.OnDismissListener, AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.extras_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listExtras = arguments?.getStringArray(getString(R.string.key_extras_dialog_vals)) as Array<String>
        initialiseUI(view)
    }

    override fun onClick(view: View) {
        when(view.id) {
//            R.id.id_et_horse_name -> {
//                listExtras[2] = etHorseName.text.toString()
//            }
            R.id.id_btn_extras_ok -> {
                listExtras[2] = etHorseName.text.toString()
                EventBus.getDefault().post(ExtrasMessage(listExtras))
                this.dismiss()
            }
            R.id.id_btn_extras_cancel -> {
                // Basically do nothing.
                this.dismiss()
            }
        }
    }

    /* Note:
       This will fire 1st when dialog loads. The extras list will have "default" values at least.
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when(parent.id) {
            R.id.id_spn_trainer -> {
                listExtras[0] = parent.getItemAtPosition(position).toString()
            }
            R.id.id_spn_jockey -> {
                listExtras[1] = parent.getItemAtPosition(position).toString()
            }
        }
    }

    // Required. TBA.
    override fun onNothingSelected(parent: AdapterView<*>?) { }

    private fun initialiseUI(view: View) {
        spnTrainer = view.findViewById(R.id.id_spn_trainer)
        spnTrainer.onItemSelectedListener = this

        ArrayAdapter.createFromResource(this.context!!, R.array.trainers_array, android.R.layout.simple_spinner_dropdown_item)
                .also{adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnTrainer.adapter = adapter}


        spnJockey = view.findViewById(R.id.id_spn_jockey)
        spnJockey.onItemSelectedListener = this

        ArrayAdapter.createFromResource(this.context!!, R.array.jockeys_array, android.R.layout.simple_spinner_dropdown_item)
                .also{adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnJockey.adapter = adapter}

        etHorseName = id_et_horse_name
        etHorseName.setOnClickListener(this)

        btnCancel = id_btn_extras_cancel
        btnCancel.setOnClickListener(this)

        btnOk = id_btn_extras_ok
        btnOk.setOnClickListener(this)
    }

    private lateinit var btnCancel: Button
    private lateinit var btnOk: Button
    private lateinit var spnTrainer: Spinner
    private lateinit var spnJockey: Spinner
    private lateinit var etHorseName: EditText
    private var listExtras = arrayOf("","","")    // backing data (user selections).

}