package mcssoft.com.racereminderac.utility.eventbus

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param msg - The message.
 * @param dId - The id of the originating dialog.
 * @param ctx - A value to give "context" to the message.
 */
class TimeMessage(var msg: String, var dId: Int, val ctx: Int) {

    val message: String get() = msg

    val ident: Int get() = dId

    val contxt: Int get() = ctx
}