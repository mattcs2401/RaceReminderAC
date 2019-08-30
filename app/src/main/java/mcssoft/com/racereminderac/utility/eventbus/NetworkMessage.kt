package mcssoft.com.racereminderac.utility.eventbus

/**
 * TBA
 */
class NetworkMessage(var enabled: Boolean, var type: String?) {
    val isEnabled: Boolean get() = enabled
    val theType: String? get() = type
}
