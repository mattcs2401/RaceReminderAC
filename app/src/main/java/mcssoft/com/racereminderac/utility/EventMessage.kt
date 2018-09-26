package mcssoft.com.racereminderac.utility

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param msg - The message.
 * @param dId - The id of the originating dialog.
 * @param ctx - TBA
 */
class EventMessage(var msg: String, var dId: Int, val ctx: Int) {

    val message: String get() = msg

    val ident: Int get() = dId

    val contex: Int get() = ctx
}