package mcssoft.com.racereminderac.utility.eventbus

class SelectMessage(var selType: Int, var pos: Int, var multiSel: Boolean) {

    val getSelType: Int get() = selType
    val getPos: Int get() = pos
    val getMultiSel: Boolean get() = multiSel
}