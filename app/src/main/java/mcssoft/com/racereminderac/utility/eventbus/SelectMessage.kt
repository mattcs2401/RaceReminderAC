package mcssoft.com.racereminderac.utility.eventbus

import android.view.View

/**
 * EventBus selection message.
 * @param selType: The type of selection, single press (tap), or long press.
 *                 Defined by constants: ITEM_SELECT, ITEM_LONG_SELECT.
 * @param pos: The selected item's position in the adapter.
 * @param view: The view associated with the selected item.
 *              Note: Can be NULL.
 */
class SelectMessage(private var selType: Int, private var pos: Int, private var view: View?) {

    val getSelType: Int get() = selType
    val getPos: Int get() = pos
    val getView: View? get() = view
}