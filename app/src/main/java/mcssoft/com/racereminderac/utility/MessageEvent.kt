package mcssoft.com.racereminderac.utility

class MessageEvent(var msg: String, var dialogId: Int) {

    fun getMessage(): String {
        return msg
    }

    fun getDialogIdent(): Int {
        return dialogId
    }
}