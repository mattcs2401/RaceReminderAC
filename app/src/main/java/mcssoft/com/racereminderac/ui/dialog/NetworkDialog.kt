package mcssoft.com.racereminderac.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.pref_dialog_fragment.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.Constants

class NetworkDialog : DialogFragment(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pref_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TBA - get arguments here ?
        initialiseUI(view)
    }

    override fun onClick(view: View) {
        val bp="bp"
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        val bp="bp"
    }

    private fun initialiseUI(view: View) {
        rgNetwork = id_rg_network_prefs
        rgNetwork.setOnCheckedChangeListener(this)
        rgNetwork.setOnClickListener(this)
        rbNone = id_rb_none
        rbWiFi = id_rb_wifi
        rbMobile = id_rb_mobile
        rbEither = id_rb_either
    }

    private lateinit var rgNetwork: RadioGroup
    private lateinit var rbNone: RadioButton
    private lateinit var rbWiFi: RadioButton
    private lateinit var rbMobile: RadioButton
    private lateinit var rbEither: RadioButton
}