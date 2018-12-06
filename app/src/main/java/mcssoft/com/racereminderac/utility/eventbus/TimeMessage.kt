package mcssoft.com.racereminderac.utility.eventbus

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param timeVal - The message value (a Calendar value in mSec).
 */
class TimeMessage(var timeVal: Long) {

    val time: Long get() = timeVal
}