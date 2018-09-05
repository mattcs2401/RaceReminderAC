package mcssoft.com.racereminderac.interfaces;

/**
 * Interface between the custom keyboard and EditFragment.
 * Returns: the component id, keycode of the key pressed.
 */
public interface IFinishKeyboard {
    void onFinishKeyboard(int compId, int keyCode);
}

