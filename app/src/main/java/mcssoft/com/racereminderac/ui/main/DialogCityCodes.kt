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
import mcssoft.com.racereminderac.R

class DialogCityCodes : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.city_codes, container, false)
        initialise(rootView)
        title.setText(R.string.city_codes_title)
        return rootView
    }

    override fun onClick(view: View) {
        val id = view.id
        when(id) {
            R.id.id_btn_cc_ok -> {
                if(!btnLetter.equals("")) {
                    // call thru to the interface.
//                    (activity as IShowCodes).onFinishCodes(R.integer.city_codes_dialog_id, btnLetter)
                    val bundle = Bundle()
                    bundle.putString("letter_key", btnLetter)
                    bundle.putString("dialog_key", "city_codes")
                    Navigation.findNavController(activity!!, R.id.id_nav_host_fragment)
                            .navigate(R.id.id_edit_fragment, bundle)
                } else {
                    Snackbar.make(view, "Must select a city code letter first.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                }
            }
            // get the letter associated with the button.
            else -> btnLetter = (view.findViewById<Button>(id)).text.toString()
        }
    }

    private fun initialise(view: View) {
        title = view.findViewById(R.id.id_tv_title)
        a_btn = view.findViewById(R.id.id_btn_A)
        a_btn.setOnClickListener(this)
        b_btn = view.findViewById(R.id.id_btn_B)
        b_btn.setOnClickListener(this)
        c_btn = view.findViewById(R.id.id_btn_C)
        c_btn.setOnClickListener(this)
        d_btn = view.findViewById(R.id.id_btn_D)
        d_btn.setOnClickListener(this)
        e_btn = view.findViewById(R.id.id_btn_E)
        e_btn.setOnClickListener(this)
        f_btn = view.findViewById(R.id.id_btn_F)
        f_btn.setOnClickListener(this)
        i_btn = view.findViewById(R.id.id_btn_I)
        i_btn.setOnClickListener(this)
        l_btn = view.findViewById(R.id.id_btn_L)
        l_btn.setOnClickListener(this)
        m_btn = view.findViewById(R.id.id_btn_M)
        m_btn.setOnClickListener(this)
        n_btn = view.findViewById(R.id.id_btn_N)
        n_btn.setOnClickListener(this)
        o_btn = view.findViewById(R.id.id_btn_O)
        o_btn.setOnClickListener(this)
        p_btn = view.findViewById(R.id.id_btn_P)
        p_btn.setOnClickListener(this)
        q_btn = view.findViewById(R.id.id_btn_Q)
        q_btn.setOnClickListener(this)
        s_btn = view.findViewById(R.id.id_btn_S)
        s_btn.setOnClickListener(this)
        t_btn = view.findViewById(R.id.id_btn_T)
        t_btn.setOnClickListener(this)
        v_btn = view.findViewById(R.id.id_btn_V)
        v_btn.setOnClickListener(this)
        x_btn = view.findViewById(R.id.id_btn_X)
        x_btn.setOnClickListener(this)
        z_btn = view.findViewById(R.id.id_btn_Z)
        z_btn.setOnClickListener(this)
        ok_btn = view.findViewById(R.id.id_btn_cc_ok)
        ok_btn.setOnClickListener(this)
    }

    //<editor-fold defaultstate="collapsed" desc="Region: Private vars">
    private var btnLetter: String = ""
    private lateinit var title: TextView
    private lateinit var a_btn: Button
    private lateinit var b_btn: Button
    private lateinit var c_btn: Button
    private lateinit var d_btn: Button
    private lateinit var e_btn: Button
    private lateinit var f_btn: Button
    private lateinit var i_btn: Button
    private lateinit var l_btn: Button
    private lateinit var m_btn: Button
    private lateinit var n_btn: Button
    private lateinit var o_btn: Button
    private lateinit var p_btn: Button
    private lateinit var q_btn: Button
    private lateinit var s_btn: Button
    private lateinit var t_btn: Button
    private lateinit var v_btn: Button
    private lateinit var x_btn: Button
    private lateinit var z_btn: Button
    private lateinit var ok_btn: Button
    //</editor-fold>
}