package mcssoft.com.racereminderac.utility.callback

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities

class NetworkCallback : ConnectivityManager.NetworkCallback() {

    override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
        super.onCapabilitiesChanged(network, networkCapabilities)
    }

    override fun onLost(network: Network?) {
        super.onLost(network)
    }

    override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
        super.onLinkPropertiesChanged(network, linkProperties)
    }

    override fun onUnavailable() {
        super.onUnavailable()
    }

    override fun onLosing(network: Network?, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
    }

    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
    }
}