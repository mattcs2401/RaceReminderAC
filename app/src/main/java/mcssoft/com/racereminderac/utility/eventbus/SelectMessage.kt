package mcssoft.com.racereminderac.utility.eventbus

class SelectMessage(var selType: Int, var pos: Int) {

    val getSelType: Int get() = selType
    val getPos: Int get() = pos
}