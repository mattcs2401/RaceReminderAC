package mcssoft.com.racereminderac.interfaces

/**
 * Interface between the custom keyboard and EditFragment.
 * Returns: the component id, keycode of the key pressed.
 */
internal interface IKeyboard {
    fun onFinishKeyboard()  //int compId, int keyCode);
}

