package mcssoft.com.racereminderac.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.number_pad.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.utility.EventMessage
import org.greenrobot.eventbus.EventBus

class NumberPadDialog : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.number_pad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseViews() //view)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.id_btn_np_done -> {
                val npCtx = arguments?.getInt("key")
                EventBus.getDefault().post(EventMessage(number, R.integer.number_pad_dialog_id, npCtx!!))
                this.dialog.cancel()
            }
            else -> {
                number = (view as Button).text.toString()
                if(!id_btn_np_done.isEnabled) {
                    id_btn_np_done.isEnabled = true
                }
            }
        }
    }

    private fun initialiseViews() { //view: View) {
        id_btn_9.setOnClickListener(this)
        id_btn_8.setOnClickListener(this)
        id_btn_7.setOnClickListener(this)
        id_btn_6.setOnClickListener(this)
        id_btn_5.setOnClickListener(this)
        id_btn_4.setOnClickListener(this)
        id_btn_3.setOnClickListener(this)
        id_btn_2.setOnClickListener(this)
        id_btn_1.setOnClickListener(this)
        id_btn_0.setOnClickListener(this)

        id_btn_np_done.setOnClickListener(this)
        id_btn_np_done.isEnabled = false
    }

    private lateinit var number: String
}