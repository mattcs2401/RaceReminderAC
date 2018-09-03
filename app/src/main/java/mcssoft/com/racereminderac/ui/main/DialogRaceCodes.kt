package mcssoft.com.racereminderac.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.edit_fragment.*
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.IShowCodes

class DialogRaceCodes : DialogFragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.race_codes, container, false)
        initialise(rootView)
        title.setText(R.string.race_codes_title)
        return rootView
    }

    override fun onClick(view: View) {
        val id = view.id
        when(id) {
            R.id.id_rc_btn_ok -> {
                if(!btnLetter.equals("")) {
                    // call thru to the interface.
                    val bundle = Bundle()
                    bundle.putString("letter_key", btnLetter)
                    bundle.putString("dialog_key", "race_codes")
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                            .navigate(R.id.id_edit_fragment, bundle)
                } else {
                    Snackbar.make(view, "Must select a race code letter first.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                }
            }
            // get the letter associated with the button.
            else -> btnLetter = (view.findViewById<Button>(id)).text.toString()
        }
    }

    private fun initialise(view: View) {
        title = view.findViewById(R.id.id_rc_tv_title)
        r_btn = view.findViewById(R.id.id_rc_btn_R)
        r_btn.setOnClickListener(this)
        t_btn = view.findViewById(R.id.id_rc_btn_T)
        t_btn.setOnClickListener(this)
        g_btn = view.findViewById(R.id.id_rc_btn_G)
        g_btn.setOnClickListener(this)
        s_btn = view.findViewById(R.id.id_rc_btn_S)
        s_btn.setOnClickListener(this)
        ok_btn = view.findViewById(R.id.id_rc_btn_ok)
        ok_btn.setOnClickListener(this)
    }

    private lateinit var rootView: View

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private var btnLetter: String = ""
    private lateinit var title: TextView
    private lateinit var r_btn: Button
    private lateinit var t_btn: Button
    private lateinit var g_btn: Button
    private lateinit var s_btn: Button
    private lateinit var ok_btn: Button
    //</editor-fold>
}