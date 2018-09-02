package mcssoft.com.racereminderac.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import mcssoft.com.racereminderac.R

class DialogRaceCodes : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.race_codes, container, false)
        return rootView
    }

    private lateinit var rootView: View
}