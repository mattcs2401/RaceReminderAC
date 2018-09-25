package mcssoft.com.racereminderac.utility

/**
 * Utility class to represent an event message posted on the EventBus.
 * @param msg - The message.
 * @param dId - The id of the originating dialog.
 */
class EventMessage(var msg: String, var dId: Int) {

    val message: String get() = msg
//    fun getMessage(): String {
//        return msg
//    }

    val ident: Int get() = dId
//    fun getDialogIdent(): Int {
//        return dId
//    }

}