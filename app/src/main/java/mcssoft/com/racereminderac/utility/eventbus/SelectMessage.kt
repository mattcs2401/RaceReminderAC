package mcssoft.com.racereminderac.utility.eventbus

/**
 * EventBus selection message.
 * @param selType: The type of selection, single press (tap), or long press.
 *                 Defined by constants: ITEM_SELECT, ITEM_LONG_SELECT.
 * @param pos: The selected item's position in the adapter.
 */
class SelectMessage(private var selType: Int, private var pos: Int) {

    val getSelType: Int get() = selType
    val getPos: Int get() = pos
}