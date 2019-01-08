package mcssoft.com.racereminderac.utility.eventbus

class SelectMessage(var selType: Int, var id: Int) {

    val getSelType: Int get() = selType
    val getId: Int get() = id
}