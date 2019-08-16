package mcssoft.com.racereminderac.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkInfo = connMgr.activeNetworkInfo

        isConnected = networkInfo.isConnected == true
    }

    fun isConnected() = this.isConnected

    fun isWiFi() = isConnected && networkInfo.type == ConnectivityManager.TYPE_WIFI

    fun isMobile() = isConnected && networkInfo.type == ConnectivityManager.TYPE_MOBILE


    private var isConnected: Boolean = false

    private lateinit var networkInfo: NetworkInfo
}