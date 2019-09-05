package mcssoft.com.racereminderac.utility.singleton

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import mcssoft.com.racereminderac.interfaces.IDownload
import mcssoft.com.racereminderac.utility.Constants
import mcssoft.com.racereminderac.utility.singleton.base.SingletonBase

class NetworkManager private constructor (private val context: Context) {

    private val connMgr: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    companion object : SingletonBase<NetworkManager, Context>(::NetworkManager)

    fun queueRequest(raceUrl: String, name: String) =
        RaceDownloadManager.getInstance(context).downLoad(raceUrl, name)

    /**
     * Get if a network connection exists.
     * @return True if connection exists, else false.
     */
    fun isNetworkConnected(): Boolean {
        return if(connMgr.activeNetworkInfo != null) {
            connMgr.activeNetworkInfo.isConnected
        } else {
            false
        }
    }

    /**
     * Get the network transport type.
     * @return Type as : NETWORK_MOB, NETWORK_WIFI or NETWORK_NONE.
     * Note: If WiFi and Mob are both selected, then WiFi seems to take preference on the system.
     *       That's possibly ok ?
     */
    fun getTransport() : Int {
        if(isNetworkConnected()) {
            if (networkCapabilities.hasTransport(TRANSPORT_CELLULAR)) {
                return Constants.NETWORK_MOB
            } else if (networkCapabilities.hasTransport(TRANSPORT_WIFI)) {
                return Constants.NETWORK_WIFI
            }
        }
        return Constants.NETWORK_NONE
    }

//    val isNetworkActive : Boolean
//        get() = connMgr.isDefaultNetworkActive
//
//    val activeNetworkInfo: NetworkInfo?
//        get() = connMgr.activeNetworkInfo
//
//    val allNetworks: Array<out Network>?
//        get() = connMgr.getAllNetworks()

    private val networkCapabilities: NetworkCapabilities
        get() = connMgr.getNetworkCapabilities(connMgr.activeNetwork)

}