package mcssoft.com.racereminderac.utility

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText

class RaceEditText : EditText {

    constructor(context: Context) : super(context) { }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        val bp = ""
        return super.onKeyPreIme(keyCode, event)
    }
}