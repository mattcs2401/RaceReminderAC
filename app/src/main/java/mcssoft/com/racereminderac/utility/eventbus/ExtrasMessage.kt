package mcssoft.com.racereminderac.utility.eventbus

class ExtrasMessage(private var vals: Array<String>?) {

    val values: Array<String> get() = vals!!
//    val trainer: String get() = values[0]
//    val jockey: String get() = values[1]
//    val horse: String get() = values[2]
}