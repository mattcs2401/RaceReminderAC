package mcssoft.com.racereminderac.utility.eventbus

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param adapterPos - The position of the selected row in the Race adapter.
 * @param updateTo   - The id of the UI component, relative to the Race object column value.
 * @param valueTo    - The value to set.
 */
class UpdateMessage(private var adapterPos: Int, private var updateTo: Int, private var valueTo: Any) {

    val pos: Int get() = adapterPos
    val update: Int get() = updateTo
    val value: Any get() = valueTo
}